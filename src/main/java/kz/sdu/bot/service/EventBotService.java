package kz.sdu.bot.service;

import kz.sdu.entity.Event;
import kz.sdu.entity.User;
import kz.sdu.service.EventBotRepositoryService;
import kz.sdu.service.StudentService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Slf4j
@Service
public class EventBotService {
    private String username;
    private String name;
    private String surname;

    private User user;

    private static EventMessageHandlingService eventMessageHandlingService;

    private final EventBotRepositoryService service;

    public EventBotService(EventBotRepositoryService service) {
        this.service = service;
        eventMessageHandlingService = new EventMessageHandlingService(
                service.getUserService(), service.getEventService());
    }

    public void telegramPreprocessing(Update update) {
        user = service.authUser(update);
    }

    public SendPhoto showEvents() {
        //fixme if event will empty, try to handling
        return eventMessageHandlingService.sendEventMessage(
                user.getTelegramAccount().getChatId(),
                this.getUser().getTelegramAccount(),
                "/events_account&index=" + 0);// sending this message

    }

    public EditMessageReplyMarkup editEventMessage(Update update, Long idEvent, String callbackData) {
        // Getting events from database, and getting id in events
        List<Event> events = service.getEventService().getEvents();
        Long[] eventsId = service.getEventService().getEventsID(events);

        // generate edit message

        return new EditMessageReplyMarkup(
                user.getTelegramAccount().getChatId(),
                this.getUser().getTelegramAccount().getActivity().getLatestMessageId(),
                update.getCallbackQuery().getInlineMessageId(),
                eventMessageHandlingService.postViewMarkup(
                        events.get(
                                Arrays.binarySearch(eventsId, idEvent)),
                        this.getUser().getTelegramAccount(),
                        callbackData));
    }

    public SendMessage editAccount(String callback) {
        SendMessage message = eventMessageHandlingService.sendEditMessage(
                callback,
                user.getTelegramAccount().getChatId());
        // check if it is account setting, or we can do edit this account
        if (callback.length() == 13) {
            return (eventMessageHandlingService.getEditAccountTools(
                    user.getTelegramAccount().getChatId(),
                    user));
        } else {
            this.getUser().getTelegramAccount().getActivity().setLatestMessage(message);
            return message;
        }

    }

    public SendMessage showAccount() {
        return eventMessageHandlingService.getAccountMessageInformation(
                user.getTelegramAccount().getChatId(),
                this.getUser());
    }

    public SendMessage defaultAction(Update update) {
        // default commands to edit account. Latest saved message will contain with text
        String command = "null";
        String text = this.getUser().getTelegramAccount().getActivity().getLatestMessage().getText();
        if (text.contains("surname")) {
            command = "/edit_account_surname";
        } else if (text.contains("name")) {
            command = "/edit_account_name";
        } else if (text.contains("student ID")) {
            command = "/edit_account_student_id";
        }
        if (!setInfoEdit(
                this.user,
                command,
                update.getMessage().getText())) {
            //if user will wrong give answer, we send a message with text
            //At this moment we don't set to The Latest Message, cause all edit, will function
            //not buttons
            return new SendMessage(
                    user.getTelegramAccount().getChatId(),
                    "Please try again");
        }

        //when user give answer bot otherwise send message that will show the account
        return eventMessageHandlingService
                .getAccountMessageInformation(
                        user.getTelegramAccount().getChatId(),
                        this.getUser());
    }

    public DeleteMessage deleteEventMessage(String chatId) throws TelegramApiException {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(getUser().getTelegramAccount().getActivity().getLatestMessageId());

        return deleteMessage;
    }

    public boolean setInfoEdit(User user, String command, String text) {
        text = text.trim();
        switch (command) {
            case "/edit_account_name" -> {
                user.setName(text);
                service.getUserService().updateAndSave(user);
            }
            case "/edit_account_surname" -> {
                user.setSurname(text);
                service.getUserService().updateAndSave(user);
            }
            case "/edit_account_student_id" -> {
                if (StudentService.studentIDChecking(text)) {
                    user.getStudent().setStudentID(text);
                    service.getUserService().updateAndSave(user);
                } else return false;
            }
        }
        return true;
    }

//    public void editEventMessage(String chatId, String text, InlineKeyboardMarkup markup) {
//        Integer messageId = user.getTelegramAccount().getActivity().getLatestMessageId();
//
//        assert markup != null;
//        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
//        editMessageReplyMarkup.setChatId(chatId);
//        editMessageReplyMarkup.setMessageId(messageId);
//        editMessageReplyMarkup.setReplyMarkup(markup);
//
//        assert text != null;
//        EditMessageText editMessageText = new EditMessageText(text);
//        editMessageText.setChatId(chatId);
//        editMessageText.setMessageId(messageId);
//
//        try {
//            execute(editMessageText);
//            execute(editMessageReplyMarkup);
//
//            log.info(
//                    "Message has been changed.\n" +
//                            "Current message id: {}", messageId);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
}
