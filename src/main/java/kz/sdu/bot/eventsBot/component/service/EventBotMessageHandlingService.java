package kz.sdu.bot.eventsBot.component.service;

import kz.sdu.bot.service.AuthorizationTelegramService;
import kz.sdu.bot.service.SendMessagesService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@EqualsAndHashCode(callSuper = false)
@Data
public final class EventBotMessageHandlingService extends EventBotService{

    public EventBotMessageHandlingService(Update update) {
        super(update.getMessage().getChat().getId(), update.getMessage().getChatId().toString(),
                new AuthorizationTelegramService.authLogUser(
                        update.getMessage().getChat().getId(),
                        update.getMessage().getChatId().toString()));

    }

    public void showEvents() {
        //fixme if event will empty, try to handling
        try {
        SendPhoto sendPhoto = EventMessageHandlingService.sendEventMessage(getChatId(), this.getTelegramAccount(),
                "/events_account&index=" + 0); //Create message with photo. Starting index - 0
            this.getTelegramAccount().getActivity().setLatestMessageId(
                    execute(sendPhoto)
                            .getMessageId() // sending this message
            );
            logger.info("Accepted executing event. Current index - {}", 0);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            logger.error("Failed execute the event. Check connection or message event");
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }


    public void showAccount() {
        try {
            this.getTelegramAccount().getActivity().setLatestMessageId(
                    execute(SendMessagesService.getAccountMessageInformation(getChatId(), this.getTelegramAccount())).getMessageId()
            ); //first will execute message with account information
            // second will set current id to LatestMessageId
            logger.info("Accepted executing account\n{}", this.getTelegramAccount());
        } catch (TelegramApiException e) {
            e.printStackTrace();
            logger.error("The account could not be displayed because it was not found in" +
                    " the list of active accounts. Check list from database or array of accounts");
        }
    }

    public void defaultAction(Update update) { // default commands to edit account. Latest saved message will contain with text
        String command = "null";
        String text = this.getTelegramAccount().getActivity().getLatestMessage().getText();
        if (text.contains("surname")) {
            command = "/edit_account_surname";
        } else if (text.contains("name")) {
            command = "/edit_account_name";
        } else if (text.contains("student ID")) {
            command = "/edit_account_student_id";
        }
        if (!this.getTelegramAccount().setInfoEdit(command, update.getMessage().getText())) {
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
            execute(SendMessagesService.getAccountMessageInformation(getChatId(), this.getTelegramAccount()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
