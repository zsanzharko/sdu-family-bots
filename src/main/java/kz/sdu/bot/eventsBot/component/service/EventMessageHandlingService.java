package kz.sdu.bot.eventsBot.component.service;

import kz.sdu.bot.service.SendMessagesService;
import kz.sdu.bot.utils.InlineKeyboardMarkupTemplate;
import kz.sdu.entity.Event;
import kz.sdu.entity.person.Account;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Objects;

public final class EventMessageHandlingService extends SendMessagesService {

    public static InlineKeyboardMarkup postViewMarkup(Event event, Account account, String callbackData) {
        InlineKeyboardMarkup scroll = new InlineKeyboardMarkup();

        String[] leaf_text = new String[0];
        String[] leaf_callbacks = new String[0];
        String like_text;
        String like_callback;

        int index = Integer.parseInt(callbackData.substring(
                callbackData.indexOf("&index=") + 7
        ));

        if (Event.getRecentEvents().size() > 1) {

            leaf_text = new String[]{"◀️", "▶️"};

            //todo test this
            if (index == 0) {
                leaf_callbacks = new String[] {
                        "/events_account&index=" + (Event.getEventList().size() - 1),
                "/events_account&index=" + (index + 1)
                };
            } else if (index == Event.getRecentEvents().size() - 1) {
                leaf_callbacks = new String[] {
                        "/events_account&index=" + (index - 1),
                "/events_account&index=" + 0
                };
            } else {
                leaf_callbacks = new String[]{
                        "/events_account&index=" + (index - 1),
                        "/events_account&index=" + (index + 1)
                };
            }
        }
        like_text = "❤️Like❤️";
        //todo test this. Or rewrite code
        like_callback = "/save_like_event&id=" + event.getID() + "&index=" +
                (index < Event.getRecentEvents().size() - 1 ? index + 1 : 0);

        for (int i = 0; i < account.getUser().getEventService().getFavoriteEvent().size(); i++)
            if (Objects.equals(account.getUser().getEventService().getFavoriteEvent().get(i).getID(), event.getID())) {
                like_text = "👎Unlike👎";
                like_callback = "/remove_like_event&id=" +
                        account.getUser().getEventService().getFavoriteEvent().get(i).getID() + "&index=" + i;
                break;
            }

        String paidButtonText;
        String paidButtonCallback;

        if (event.getTicket().isPaid()) {
            paidButtonText = "💵Buy";
            paidButtonCallback = "/buy_ticket_event";
        } else {
            paidButtonText = "Register";
            paidButtonCallback = "/register_ticket_event";
        }

        scroll.setKeyboard(
                new InlineKeyboardMarkupTemplate.Builder()
                        .addToColumn(
                                List.of(leaf_text),
                                List.of(leaf_callbacks))
                        .addToRow(like_text, like_callback)
                        .addToRow(paidButtonText, paidButtonCallback)
                        .build().getButtons()
        );
        return scroll;
    }

    public static InlineKeyboardMarkup favoritePostViewMarkup(Event event, Account account, String command) {
        int index = Integer.parseInt(command.substring(command.indexOf("&index=") + 7));
        InlineKeyboardMarkup scroll = new InlineKeyboardMarkup();

        String[] leaf_text = new String[0];
        String[] leaf_callbacks = new String[0];
        String like_text = "❤️Like❤️";
        String like_callback;

        if (account.getUser().getEventService().getFavoriteEvent().size() > 1) {

            leaf_text = new String[]{"◀️", "▶️"};

            if (index == 0) {
                leaf_callbacks = new String[] {
                        "/liked_events_account&index=" +
                                (account.getUser().getEventService().getFavoriteEvent().size() - 1),
                        "/liked_events_account&index=" + (index + 1)};
            } else if (index == account.getUser().getEventService().getFavoriteEvent().size() - 1) {
                leaf_callbacks = new String[]{
                        "/liked_events_account&index=" + (index - 1),
                        "/liked_events_account&index=" + 0
                };
            } else {
                leaf_callbacks = new String[]{
                        "/liked_events_account&index=" + (index - 1),
                        "/liked_events_account&index=" + (index + 1)
                };
            }
        }

        like_callback = "/save_like_event&id=" + event.getID() + "&index=" +
                (index < Event.getRecentEvents().size() - 1 ? index + 1 : 0);

        for (int i = 0; i < account.getUser().getEventService().getFavoriteEvent().size(); i++) {
            if (Objects.equals(account.getUser().getEventService().getFavoriteEvent().get(i).getID(), event.getID())) {
                like_text = "👎Unlike👎";
                like_callback = "/remove_like_event&account&id=" + account.getUser().
                        getEventService().getFavoriteEvent().get(i).getID();
                break;
            }
        }

        String paidButtonText;
        String paidButtonCallback;

        if (event.getTicket().isPaid()) {
            paidButtonText = "💵Buy";
            paidButtonCallback = "/buy_ticket_event";
        } else {
            paidButtonText = "Register";
            paidButtonCallback = "/register_ticket_event";
        }

        scroll.setKeyboard(
                new InlineKeyboardMarkupTemplate.Builder()
                        .addToColumn(
                                List.of(leaf_text),
                                List.of(leaf_callbacks))
                        .addToRow(like_text, like_callback)
                        .addToRow(paidButtonText, paidButtonCallback)
                        .build().getButtons()
        );
        return scroll;
    }


    public static SendPhoto sendEventMessage(String chatId, Account account, String callbackData) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        int index = Integer.parseInt(callbackData.substring(
                callbackData.indexOf("&index=") + 7
        ));
        sendPhoto.setCaption(Event.getRecentEvents().get(index).getInformation());

        if (Event.getRecentEvents().get(index).getImage() != null) {
            sendPhoto.setPhoto(new InputFile(Event.getRecentEvents().get(index).getImage()));
        }

        if (callbackData.contains("/events")) {
            sendPhoto.setReplyMarkup(postViewMarkup(Event.getRecentEvents().get(index), account, callbackData));
        } else if (callbackData.contains("/liked_events_account")) {
            sendPhoto.setReplyMarkup(favoritePostViewMarkup(
                    account.getUser().getEventService().getFavoriteEvent().get(index), account, callbackData));
        }
        LoggerFactory.getLogger(EventMessageHandlingService.class)
                .info("Picked information");
        return sendPhoto;
    }
}