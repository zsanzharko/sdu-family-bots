package kz.sdu.bot.eventsBot.service;

import kz.sdu.bot.entity.person.Account;
import kz.sdu.bot.eventsBot.component.EventsBotApp;
import kz.sdu.bot.utils.SendMessages;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@EqualsAndHashCode(callSuper = true)
@Data
public class EventBotService extends EventsBotApp {
    private Long id;
    private String chatId;
    private String username;
    private String name;
    private String surname;

    private Account account;

    final Logger logger = LoggerFactory.getLogger(EventBotService.class);

    public EventBotService() {
    }

    public void setUpdate(Update update) {
        // Getting data in user
        id = update.getMessage().getChat().getId();
        chatId = update.getMessage().getChatId().toString();
        username = update.getMessage().getChat().getUserName() != null ?
                update.getMessage().getChat().getUserName() :
                "null"; // if user hidden username in policy, we give null
        name = update.getMessage().getChat().getFirstName();
        surname = update.getMessage().getChat().getLastName();

        account = AuthorizationTelegramService.authLogPerson(id, chatId, username, name, surname); // authorize user in bot session and ecosystem
    }

    public void showEvents() {
        SendPhoto sendPhoto = SendMessages.sendEventMessage(chatId, account,
                "/events_account&index=" + 0); //Create message with photo. Starting index - 0
        try {
            account.getActivity().setLatestMessageId(
                    execute(sendPhoto)
                            .getMessageId() // sending this message
            );
            logger.info("Accepted executing event. Current index - {}", 0);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            logger.error("Failed execute the event. Check connection or message event");
        }
    }

    public void showAccount() {
        try {
            account.getActivity().setLatestMessageId(
                    execute(SendMessages.getAccountMessageInformation(chatId, account)).getMessageId()
            ); //first will execute message with account information
            // second will set current id to LatestMessageId
            logger.info("Accepted executing account\n{}", account);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            logger.error("The account could not be displayed because it was not found in" +
                    " the list of active accounts. Check list from database or array of accounts");
        }
    }

    public void defaultAction(Update update) { // default commands to edit account. Latest saved message will contain with text
        String command = "null";
        String text = account.getActivity().getLatestMessage().getText();
        if (text.contains("surname")) {
            command = "/edit_account_surname";
        } else if (text.contains("name")) {
            command = "/edit_account_name";
        } else if (text.contains("student ID")) {
            command = "/edit_account_student_id";
        }
        if (!account.setInfoEdit(command, update.getMessage().getText())) {
            try {
                //if user will wrong give answer, we send a message with text
                //At this moment we don't set to The Latest Message, cause all edit, will function
                //not buttons
                execute(SendMessages.sendMessage(chatId, "Please try again"));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        //when user give answer bot otherwise send message that will show the account
        try {
            execute(SendMessages.getAccountMessageInformation(chatId, account));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    public void deleteEventMessage(String chatId, Account account) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(account.getActivity().getLatestMessageId());

        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
