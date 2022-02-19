package kz.sdu.bot.service;

import kz.sdu.bot.utils.InlineKeyboardMarkupTemplate;
import kz.sdu.entity.User;
import kz.sdu.service.TelegramAccountService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

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

        return new SendMessage(chatId,text);
    }

    public static SendMessage getAccountMessageInformation(String chatId, User user) {
        SendMessage message = new SendMessage(chatId, new TelegramAccountService(user).getInformationAccount());

        final String[] texts = {"Edit", "Tickets", "❤️Events"};
        final String[] callbacks = {"/edit_account", "/tickets_account", "/liked_events_account&index=" + 0};

        InlineKeyboardMarkup markup = new InlineKeyboardMarkupTemplate.Builder()
                .addToRow(
                        List.of(texts),
                        List.of(callbacks)
                ).build().getTemplate();

        message.setReplyMarkup(markup);

        user.getTelegramAccount().getActivity().setLatestMessage(message);

        return message;
    }

    public static SendMessage getEditAccountTools(String chatId, User user) {
        SendMessage message = new SendMessage(chatId, new TelegramAccountService(user).getInformationAccount());

        final String[] column_one_texts = {"Name", "Surname"};
        final String[] column_one_callbacks = {"/edit_account_name", "/edit_account_surname"};
        final String[] row_two_texts = {"💳Student ID"};
        final String[] row_two_callbacks = {"/edit_account_student_id"};

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkupTemplate.Builder()
                .addToColumn(
                        List.of(column_one_texts),
                        List.of(column_one_callbacks))
                .addToRow(
                        List.of(row_two_texts),
                        List.of(row_two_callbacks)
                ).build().getTemplate();

        message.setReplyMarkup(inlineKeyboardMarkup);

        user.getTelegramAccount().getActivity().setLatestMessage(message);

        return message;
    }

}
