package kz.sdu.bot.service;

import kz.sdu.entity.Event;
import kz.sdu.entity.User;
import kz.sdu.service.EventBotRepositoryService;
import kz.sdu.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Slf4j
@Service
public class EventBotService {
    private Long id;
    private String chatId;
    private String username;
    private String name;
    private String surname;

    private User user;

    private final EventBotRepositoryService service;

    public EventBotService(EventBotRepositoryService service) {
        this.service = service;
    }


    public void setIds(Long id, String chatId) {
        this.id = id;
        this.chatId = chatId;
//        this.user = new AuthorizationTelegramService(accountRepository, userRepository).authLogUser(id, chatId);
    }

    public SendPhoto showEvents() {
        //fixme if event will empty, try to handling
        return new EventMessageHandlingService().sendEventMessage(getChatId(),
                this.getUser().getTelegramAccount(),
                "/events_account&index=" + 0);// sending this message

    }

    public EditMessageReplyMarkup editEventMessage(Update update, Long idEvent, String callbackData) {
        // Getting events from database, and getting id in events
//        List<Event> events = eventRepository.findAll();
        List<Event> events = null;
        Long[] eventsId = new Long[events.size()];

        for (int i = 0; i < eventsId.length; i++) {
            eventsId[i] = events.get(i).getId();
        }

        // generate edit message

        return new EditMessageReplyMarkup(
                getChatId(),
                this.getUser().getTelegramAccount().getActivity().getLatestMessageId(),
                update.getCallbackQuery().getInlineMessageId(),
                new EventMessageHandlingService().postViewMarkup(
                        events.get(
                                Arrays.binarySearch(eventsId, idEvent)),
                        this.getUser().getTelegramAccount(),
                        callbackData));
    }

    public void saveEvent(Long idEvent) {
        // Todo save event to account, using some OneToOne or other.
    }

    public SendMessage editAccount(String callback) {
        SendMessage message = SendMessagesService.sendEditMessage(callback, getChatId());
        // check if it is account setting, or we can do edit this account
        if (callback.length() == 13) {
            return (SendMessagesService.getEditAccountTools(getChatId(), this.getUser()));
        } else {
            this.getUser().getTelegramAccount().getActivity().setLatestMessage(message);
            return message;
        }

    }

    public SendMessage showAccount() {
        return SendMessagesService.getAccountMessageInformation(getChatId(),
                this.getUser());
    }

    public SendMessage defaultAction(Update update) {
        UserService userService = new UserService();
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
        if (!userService.setInfoEdit(command, update.getMessage().getText())) {
            //if user will wrong give answer, we send a message with text
            //At this moment we don't set to The Latest Message, cause all edit, will function
            //not buttons
            return new SendMessage(getChatId(), "Please try again");
        }

        //when user give answer bot otherwise send message that will show the account
        return SendMessagesService
                .getAccountMessageInformation(
                        getChatId(),
                        this.getUser());
    }

    public DeleteMessage deleteEventMessage(String chatId) throws TelegramApiException {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(getUser().getTelegramAccount().getActivity().getLatestMessageId());

        return deleteMessage;
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
