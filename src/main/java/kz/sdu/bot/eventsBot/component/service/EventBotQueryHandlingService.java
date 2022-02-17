package kz.sdu.bot.eventsBot.component.service;

import kz.sdu.bot.service.AuthorizationTelegramService;
import kz.sdu.bot.service.SendMessagesService;
import kz.sdu.bot.utils.InlineKeyboardMarkupTemplate;
import kz.sdu.entity.Event;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

public class EventBotQueryHandlingService extends EventBotService {

    private final String callbackData;
    private int index = 0;

    public EventBotQueryHandlingService(Update update) {
        super(
                update.getMessage().getChat().getId(),
                update.getMessage().getChatId().toString(),
                update.getMessage().getChat().getUserName() != null ?
                        update.getMessage().getChat().getUserName() :
                        "null",
                update.getMessage().getChat().getFirstName(),
                update.getMessage().getChat().getLastName(),
                AuthorizationTelegramService.authLogPerson(
                        update.getMessage().getChat().getId(),
                        update.getMessage().getChatId().toString(),
                        update.getMessage().getChat().getUserName() != null ?
                                update.getMessage().getChat().getUserName() :
                                "null",
                        update.getMessage().getChat().getFirstName(),
                        update.getMessage().getChat().getLastName()
                )
        );
        this.callbackData = update.getCallbackQuery().getData();
        if (callbackData.contains("&index"))
            this.index = Integer.parseInt(callbackData.substring(callbackData.indexOf("&index=") + 7));
    }

    public void editAccount() {
        SendMessage message = SendMessagesService.sendEditMessage(callbackData, getChatId());
        try {
            // check if it is account setting, or we can do edit this account
            if (callbackData.length() == 13) {
                execute(SendMessagesService.getEditAccountTools(getChatId(), getAccount()));
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
                execute(new SendMessage(getChatId(), "Your liked events is empty"));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            deleteEventMessage(getChatId());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        SendPhoto sendPhoto = EventMessageHandlingService.sendEventMessage(getChatId(), getAccount(),
                "/liked_events_account&index=" + index);
        try {
            getAccount().getActivity().setLatestMessageId(execute(sendPhoto).getMessageId());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void showEvents() {
        //            deleteEventMessage(chatId, account);
        SendPhoto sendPhoto = EventMessageHandlingService.sendEventMessage(getChatId(), getAccount(),
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
        EventService service = getAccount().getUser().getEventService();
        //remove like message to account
        service.removeFavoriteEvent(idEvent);
        if (service.getFavoriteEvent().size() == 0 && callbackData.contains("&account")) {
            try {
                deleteEventMessage(getChatId()); // delete event in message
            } catch (TelegramApiException e) {
                e.printStackTrace();
                try {
                    execute(new SendMessage(getChatId(),
                            "Sorry, we can't remove this message, because time is out"));
                } catch (TelegramApiException ex) {
                    ex.printStackTrace();
                }
            }
            SendMessage emptyListMessage = new SendMessage(getChatId(), "Your liked list is empty");
            InlineKeyboardMarkup markupTemplate = new InlineKeyboardMarkupTemplate.Builder()
                    .addToRow(
                            List.of(new String[]{"Continue watching events"}),
                            List.of(new String[]{"/events_account&index=" + index}))
                    .build()
                    .getTemplate();
            emptyListMessage.setReplyMarkup(markupTemplate);
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
                getChatId(),
                getAccount().getActivity().getLatestMessageId(),
                update.getCallbackQuery().getInlineMessageId(),
                EventMessageHandlingService.postViewMarkup(
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
