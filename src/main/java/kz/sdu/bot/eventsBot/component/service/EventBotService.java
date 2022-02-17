package kz.sdu.bot.eventsBot.component.service;

import kz.sdu.entity.TelegramAccount;
import kz.sdu.bot.eventsBot.component.EventsBotApp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@EqualsAndHashCode(callSuper = true)
@Data
public class EventBotService extends EventsBotApp {
    private Long id;
    private String chatId;
    private String username;
    private String name;
    private String surname;

    private TelegramAccount telegramAccount;

    private EventBotMessageHandlingService messageService;
    private EventBotQueryHandlingService queryHandlingService;

    final Logger logger = LoggerFactory.getLogger(EventBotService.class);

    public EventBotService() {
    }

    public EventBotService(Long id, String chatId, String username, String name, String surname, TelegramAccount telegramAccount) {
        this.id = id;
        this.chatId = chatId;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.telegramAccount = telegramAccount;
    }

    public EventBotService(Long id, String chatId, TelegramAccount telegramAccount) {
        this.id = id;
        this.chatId = chatId;
        this.telegramAccount = telegramAccount;
    }

    public void deleteEventMessage(String chatId) throws TelegramApiException {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(telegramAccount.getActivity().getLatestMessageId());

        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } finally {
            telegramAccount.getActivity().setLatestMessageId(null);
            logger.warn("Latest message has been deleted, in telegramAccount {}", telegramAccount.getId());
        }
    }

    public void editMessage(String chatId, String text, InlineKeyboardMarkup markup) {
        Integer messageId = telegramAccount.getActivity().getLatestMessageId();

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

            logger.info(
                    "Message has been changed.\n" +
                    "Current message id: {}", messageId);
        }  catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
