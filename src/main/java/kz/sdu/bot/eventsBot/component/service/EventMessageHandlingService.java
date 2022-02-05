package kz.sdu.bot.eventsBot.component.service;

import kz.sdu.bot.service.SendMessagesService;
import kz.sdu.entity.Event;
import kz.sdu.entity.person.Account;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class EventMessageHandlingService extends SendMessagesService {

    public static InlineKeyboardMarkup eventScrolling(Event event, Account account, String callbackData) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        InlineKeyboardMarkup scroll = new InlineKeyboardMarkup();
        InlineKeyboardButton like = new InlineKeyboardButton();

        int index = Integer.parseInt(callbackData.substring(
                callbackData.indexOf("&index=") + 7
        ));

        if (Event.getRecentEvents().size() > 1) {
            InlineKeyboardButton previous = new InlineKeyboardButton();
            InlineKeyboardButton next = new InlineKeyboardButton();

            previous.setText("◀️");
            next.setText("▶️");
            //todo test this
            if (index == 0) {
                previous.setCallbackData("/events_account&index=" + (Event.getEventList().size() - 1));
                next.setCallbackData("/events_account&index=" + (index + 1));
            } else if (index == Event.getRecentEvents().size() - 1) {
                previous.setCallbackData("/events_account&index=" + (index - 1));
                next.setCallbackData("/events_account&index=" + 0);
            } else {
                previous.setCallbackData("/events_account&index=" + (index - 1));
                next.setCallbackData("/events_account&index=" + (index + 1));
            }

            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(previous);
            row1.add(next);
            rowList.add(row1);
        }
        like.setText("❤️Like❤️");
        //todo test this. Or rewrite code
        like.setCallbackData("/save_like_event&id=" + event.getID() + "&index=" +
                (index < Event.getRecentEvents().size() - 1 ? index + 1 : 0));

        for (int i = 0; i < account.getUser().getEventService().getFavoriteEvent().size(); i++) {
            if (Objects.equals(account.getUser().getEventService().getFavoriteEvent().get(i).getID(), event.getID())) {
                like.setText("👎Unlike👎");
                like.setCallbackData("/remove_like_event&id=" +
                        account.getUser().getEventService().getFavoriteEvent().get(i).getID() + "&index=" + i);
                break;
            }
        }
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(like);
        rowList.add(row2);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        if (event.getTicket().isPaid()) {
            InlineKeyboardButton price = new InlineKeyboardButton();
            price.setText("💵Buy");
            price.setCallbackData("/buy_ticket_event");
            row3.add(price);
        } else {
            InlineKeyboardButton free = new InlineKeyboardButton();
            free.setText("Register");
            free.setCallbackData("/register_ticket_event");
            row3.add(free);
        }
        rowList.add(row3);
        scroll.setKeyboard(rowList);

        return scroll;
    }

    public static InlineKeyboardMarkup likedEventScrolling(Event event, Account account, String command) {
        int index = Integer.parseInt(command.substring(command.indexOf("&index=") + 7));
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        InlineKeyboardMarkup scroll = new InlineKeyboardMarkup();
        InlineKeyboardButton like = new InlineKeyboardButton();

        if (account.getUser().getEventService().getFavoriteEvent().size() > 1) {
            InlineKeyboardButton previous = new InlineKeyboardButton();
            InlineKeyboardButton next = new InlineKeyboardButton();
            previous.setText("◀️");
            next.setText("▶️");

            if (index == 0) {
                previous.setCallbackData("/liked_events_account&index=" +
                        (account.getUser().getEventService().getFavoriteEvent().size() - 1));
                next.setCallbackData("/liked_events_account&index=" + (index + 1));
            } else if (index == account.getUser().getEventService().getFavoriteEvent().size() - 1) {
                previous.setCallbackData("/liked_events_account&index=" + (index - 1));
                next.setCallbackData("/liked_events_account&index=" + 0);
            } else {
                previous.setCallbackData("/liked_events_account&index=" + (index - 1));
                next.setCallbackData("/liked_events_account&index=" + (index + 1));
            }

            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(previous);
            row1.add(next);
            rowList.add(row1);
        }

        for (int i = 0; i < account.getUser().getEventService().getFavoriteEvent().size(); i++) {
            if (Objects.equals(account.getUser().getEventService().getFavoriteEvent().get(i).getID(), event.getID())) {
                like.setText("👎Unlike👎");
                like.setCallbackData("/remove_like_event&account&id=" + account.getUser().getEventService().getFavoriteEvent().get(i).getID());
                List<InlineKeyboardButton> row2 = new ArrayList<>();
                row2.add(like);
                rowList.add(row2);
                break;
            }
        }

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        if (event.getTicket().isPaid()) {
            InlineKeyboardButton price = new InlineKeyboardButton();
            price.setText("💵Buy");
            price.setCallbackData("/buy_ticket_event");
            row3.add(price);
        } else {
            InlineKeyboardButton free = new InlineKeyboardButton();
            free.setText("Register");
            free.setCallbackData("/register_ticket_event");
            row3.add(free);
        }
        rowList.add(row3);
        scroll.setKeyboard(rowList);
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
            sendPhoto.setReplyMarkup(eventScrolling(Event.getRecentEvents().get(index), account, callbackData));
        } else if (callbackData.contains("/liked_events_account")) {
            sendPhoto.setReplyMarkup(likedEventScrolling(
                    account.getUser().getEventService().getFavoriteEvent().get(index), account, callbackData));
        }
        return sendPhoto;
    }
}
