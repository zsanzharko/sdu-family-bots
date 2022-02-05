package kz.sdu.bot.eventsBot.component.service;

import kz.sdu.entity.person.Account;
import kz.sdu.bot.eventsBot.component.EventsBotApp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
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

    private EventBotMessageHandlingService messageService;
    private EventBotQueryHandlingService queryHandlingService;

    final Logger logger = LoggerFactory.getLogger(EventBotService.class);

    public EventBotService() {
    }

    public EventBotService(Long id, String chatId, String username, String name, String surname, Account account) {
        this.id = id;
        this.chatId = chatId;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.account = account;
    }

    public EventBotService(Long id, String chatId, Account account) {
        this.id = id;
        this.chatId = chatId;
        this.account = account;
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
