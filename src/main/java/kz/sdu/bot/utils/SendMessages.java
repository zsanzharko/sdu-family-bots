package kz.sdu.bot.utils;

import kz.sdu.entity.Event;
import kz.sdu.entity.person.Account;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SendMessages {

    public static SendMessage sendMessage(String CHAT_ID, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(CHAT_ID);
        message.setText(text);

        return message;
    }

    public static SendMessage sendEditMessage(String callBackData, String chatId, Account account) {
        String text = "Enter your ";

        switch (callBackData) {
            case "/edit_account_name" -> text += "name";
            case "/edit_account_surname" -> text += "surname";
            case "/edit_account_student_id" -> text += "student ID";
            default -> text = "null";
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        return message;
    }

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

    public static SendMessage getAccountMessageInformation(String chatId, Account account) {
        SendMessage message = new SendMessage();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton edit = new InlineKeyboardButton();
        InlineKeyboardButton tickets = new InlineKeyboardButton();
        InlineKeyboardButton likedEvents = new InlineKeyboardButton();

        edit.setText("Edit");
        edit.setCallbackData("/edit_account");
        tickets.setText("Tickets");
        tickets.setCallbackData("/tickets_account");
        likedEvents.setText("❤️Events");
        likedEvents.setCallbackData("/liked_events_account&index=" + 0);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();

        row1.add(edit);
        rowList.add(row1);
        row2.add(tickets);
        rowList.add(row2);
        row3.add(likedEvents);
        rowList.add(row3);
        inlineKeyboardMarkup.setKeyboard(rowList);
        message.setChatId(chatId);
        message.setText(account.getInformationAccount());
        message.setReplyMarkup(inlineKeyboardMarkup);

        account.getActivity().setLatestMessage(message);

        return message;
    }

    public static SendMessage getEditAccountTools(String chatId, Account account) {
        SendMessage message = new SendMessage();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton name = new InlineKeyboardButton();
        InlineKeyboardButton surname = new InlineKeyboardButton();
        InlineKeyboardButton studentID = new InlineKeyboardButton();

        name.setText("Name");
        name.setCallbackData("/edit_account_name");
        surname.setText("Surname");
        surname.setCallbackData("/edit_account_surname");
        studentID.setText("💳Student ID");
        studentID.setCallbackData("/edit_account_student_id");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        row1.add(name);
        row1.add(surname);
        rowList.add(row1);
        row2.add(studentID);
        rowList.add(row2);

        inlineKeyboardMarkup.setKeyboard(rowList);
        message.setChatId(chatId);
        message.setText(account.getInformationAccount());
        message.setReplyMarkup(inlineKeyboardMarkup);

        account.getActivity().setLatestMessage(message);

        return message;
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

    public static InlineKeyboardMarkup two_choice_markup(String[] texts, String[] callback_data){
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton first_choice = new InlineKeyboardButton();
        InlineKeyboardButton second_choice= new InlineKeyboardButton();

        first_choice.setText(texts[0]);
        first_choice.setCallbackData(callback_data[0]);

        second_choice.setText(texts[1]);
        second_choice.setCallbackData(callback_data[1]);

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(first_choice);
        row.add(second_choice);

        rowList.add(row);
        markup.setKeyboard(rowList);
        return markup;
    }

    public static InlineKeyboardMarkup two_columns_markup(List<String> texts, List<String> callback_data) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        if (texts.size() != callback_data.size()) return new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (int i = 0; i < texts.size() / 2; i += 2) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int j = i; j < 2; j++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(texts.get(j));
                button.setCallbackData(callback_data.get(j));
                row.add(button);
            }
            rowList.add(row);
        }
        if (texts.size() % 2 == 1) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(texts.get(texts.size() - 1));
            button.setCallbackData(callback_data.get(callback_data.size() - 1));
            row.add(button);
            rowList.add(row);
        }
        markup.setKeyboard(rowList);
        return markup;
    }

    public static InlineKeyboardMarkup one_row_markup(List<String> texts, List<String> callback_data) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        if (texts.size() != callback_data.size()) return new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (int i = 0; i < texts.size(); i++) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(texts.get(i));
            button.setCallbackData(callback_data.get(i));
            row.add(button);
            rowList.add(row);
        }
        markup.setKeyboard(rowList);

        return markup;
    }
}
