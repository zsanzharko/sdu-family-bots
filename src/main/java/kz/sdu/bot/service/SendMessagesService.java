package kz.sdu.bot.service;

import kz.sdu.entity.person.Account;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class SendMessagesService {
    public static SendMessage sendEditMessage(String callBackData, String chatId) {
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

}
