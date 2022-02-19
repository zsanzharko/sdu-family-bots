package kz.sdu.bot.service;

import kz.sdu.entity.Event;
import kz.sdu.bot.EventsBotApp;
import kz.sdu.entity.User;
import kz.sdu.repository.EventRepository;
import kz.sdu.repository.TelegramAccountRepository;
import kz.sdu.repository.UserRepository;
import kz.sdu.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class EventBotService extends EventsBotApp {
    private Long id;
    private String chatId;
    private String username;
    private String name;
    private String surname;

    private User user;


    public void setIds(Long id, String chatId) {
        this.id = id;
        this.chatId = chatId;
//        this.user = new AuthorizationTelegramService(accountRepository, userRepository).authLogUser(id, chatId);
    }

    public void showEvents() {
        //fixme if event will empty, try to handling
        try {
            SendPhoto sendPhoto = new EventMessageHandlingService().sendEventMessage(getChatId(), this.getUser().getTelegramAccount(),
                    "/events_account&index=" + 0); //Create message with photo. Starting index - 0
            this.getUser().getTelegramAccount().getActivity().setLatestMessageId(
                    execute(sendPhoto)
                            .getMessageId() // sending this message
            );
            log.info("Accepted executing event. Current index - {}", 0);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Failed execute the event. Check connection or message event");
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
    public void editEventMessage(Update update, Long idEvent, String callbackData) {
        // Getting events from database, and getting id in events
//        List<Event> events = eventRepository.findAll();
        List<Event> events = null;
        Long[] eventsId = new Long[events.size()];

        for (int i = 0; i < eventsId.length; i++) {
            eventsId[i] = events.get(i).getId();
        }

        // generate edit message
        EditMessageReplyMarkup editMessageToUnlike = new EditMessageReplyMarkup(
                getChatId(),
                this.getUser().getTelegramAccount().getActivity().getLatestMessageId(),
                update.getCallbackQuery().getInlineMessageId(),
                new EventMessageHandlingService().postViewMarkup(
                        events.get(
                                Arrays.binarySearch(eventsId, idEvent)),
                        this.getUser().getTelegramAccount(),
                        callbackData));

        try {
            execute(editMessageToUnlike);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void saveEvent(Long idEvent) {
        // Todo save event to account, using some OneToOne or other.
    }

    public void editAccount(String callback) {
        SendMessage message = SendMessagesService.sendEditMessage(callback, getChatId());
        try {
            // check if it is account setting, or we can do edit this account
            if (callback.length() == 13) {
                execute(SendMessagesService.getEditAccountTools(getChatId(), this.getUser()));
            } else {
                execute(message);
                this.getUser().getTelegramAccount().getActivity().setLatestMessage(message);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void showAccount() {
        try {
            this.getUser().getTelegramAccount().getActivity().setLatestMessageId(
                    execute(SendMessagesService.getAccountMessageInformation(getChatId(),
                            this.getUser())).getMessageId()
            ); //first will execute message with account information
            // second will set current id to LatestMessageId
            log.info("Accepted executing account\n{}", this.getUser().getTelegramAccount());
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("The account could not be displayed because it was not found in" +
                    " the list of active accounts. Check list from database or array of accounts");
        }
    }

    public void defaultAction(Update update) {
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
            try {
                //if user will wrong give answer, we send a message with text
                //At this moment we don't set to The Latest Message, cause all edit, will function
                //not buttons
                execute(new SendMessage(getChatId(), "Please try again"));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        //when user give answer bot otherwise send message that will show the account
        try {
            execute(SendMessagesService.getAccountMessageInformation(getChatId(),
                    this.getUser()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void deleteEventMessage(String chatId) throws TelegramApiException {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(getUser().getTelegramAccount().getActivity().getLatestMessageId());

        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } finally {
            getUser().getTelegramAccount().getActivity().setLatestMessageId(null);
            log.warn("Latest message has been deleted (null), in telegramAccount {}", getUser().getTelegramAccount().getId());
        }
    }

    public void editEventMessage(String chatId, String text, InlineKeyboardMarkup markup) {
        Integer messageId = user.getTelegramAccount().getActivity().getLatestMessageId();

        assert markup != null;
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setReplyMarkup(markup);

        assert text != null;
        EditMessageText editMessageText = new EditMessageText(text);
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);

        try {
            execute(editMessageText);
            execute(editMessageReplyMarkup);

            log.info(
                    "Message has been changed.\n" +
                    "Current message id: {}", messageId);
        }  catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
