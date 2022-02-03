package kz.sdu.bot.eventsBot.service;

import kz.sdu.bot.entity.Event;
import kz.sdu.bot.utils.SendMessages;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

public class EventBotQueryHandlingService extends EventBotService {
    private String chatId;
    private String callbackData;

    private int index = 0;

    public EventBotQueryHandlingService() {
    }

    public void setUpdate(Update update) {
        // Getting data in user
        Long id = update.getCallbackQuery().getFrom().getId();
        this.chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        this.callbackData = update.getCallbackQuery().getData();

        setAccount(AuthorizationTelegramService.authLogPerson(id, chatId));

        if (callbackData.contains("&index"))
            this.index = Integer.parseInt(callbackData.substring(callbackData.indexOf("&index=") + 7));
    }

    public void editAccount() {
        SendMessage message = SendMessages.sendEditMessage(callbackData, chatId, getAccount());
        try {
            // check if it is account setting, or we can do edit this account
            if (callbackData.length() == 13) {
                execute(SendMessages.getEditAccountTools(chatId, getAccount()));
            } else {
                execute(message);
                getAccount().getActivity().setLatestMessage(message);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void showFavoriteEvents() {
        List<Event> likedEvents = getAccount().getUser().getEventService().getFavoriteEvent();
        if (likedEvents.size() == 0) {
            try {
                execute(SendMessages.sendMessage(chatId, "Your liked events is empty"));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return;
        }
        deleteEventMessage(chatId, getAccount());
        SendPhoto sendPhoto = SendMessages.sendEventMessage(chatId, getAccount(),
                "/liked_events_account&index=" + index);
        try {
            getAccount().getActivity().setLatestMessageId(execute(sendPhoto).getMessageId());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void showEvents() {
        //            deleteEventMessage(chatId, account);
        SendPhoto sendPhoto = SendMessages.sendEventMessage(chatId, getAccount(),
                "/events_account&index=" + index);
        try {
            getAccount().getActivity().setLatestMessageId(execute(sendPhoto).getMessageId());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void saveEvent(Long idEvent) {
        getAccount().getUser().getEventService().addFavoriteEvent(idEvent);
    }

    public void removeEvent(Long idEvent) {
        //remove like message to account
        getAccount().getUser().getEventService().removeFavoriteEvent(idEvent);
        if (getAccount().getUser().getEventService().getFavoriteEvent().size() == 0 && callbackData.contains("&account")) {
            deleteEventMessage(chatId, getAccount());
            SendMessage emptyListMessage = new SendMessage(chatId, "Your liked list is empty");
            emptyListMessage.setReplyMarkup(SendMessages.one_row_markup(
                    List.of(new String[]{"Continue watching events"}),
                    List.of(new String[]{"/events_account&index=" + index})));
            try {
                execute(emptyListMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void editMessage(Update update, Long idEvent) {
        Long[] eventsId = new Long[Event.getRecentEvents().size()];
        for (int i = 0; i < eventsId.length; i++) {
            eventsId[i] = Event.getEventList().get(i).getID();
        }

        EditMessageReplyMarkup editMessageToUnlike = new EditMessageReplyMarkup(
                chatId,
                getAccount().getActivity().getLatestMessageId(),
                update.getCallbackQuery().getInlineMessageId(),
                SendMessages.eventScrolling(
                        Event.getRecentEvents().get(
                                Arrays.binarySearch(eventsId, idEvent)),
                        getAccount(),
                        callbackData));

        try {
            execute(editMessageToUnlike);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
