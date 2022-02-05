package kz.sdu.bot.utils;

import kz.sdu.bot.service.SendMessagesService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardMarkupTemplate extends SendMessagesService {

    private final InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    private final List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

    public List<List<InlineKeyboardButton>> getButtons() {
        return buttons;
    }

    public InlineKeyboardMarkup getTemplate() {
        markup.setKeyboard(buttons);
        return markup;
    }

    public static class Builder {
        private final InlineKeyboardMarkupTemplate templates;

        public Builder() {
            templates = new InlineKeyboardMarkupTemplate();
        }

        public Builder addToColumn(List<String> texts, List<String> callbacks) {
            List<InlineKeyboardButton> columnList = new ArrayList<>();

            if (texts.size() != callbacks.size()) {
                columnList.add(new InlineKeyboardButton());
                templates.getButtons().add(columnList);
                return this;
            }

            for (int i = 0; i < texts.size(); i++) {
                InlineKeyboardButton button = new InlineKeyboardButton(texts.get(i));
                button.setCallbackData(callbacks.get(i));
                columnList.add(button);
            }
            templates.getButtons().add(columnList);
            return this;
        }

        public Builder addToRow(List<String> texts, List<String> callbacks) {
            if (texts.size() != callbacks.size()) {
                templates.getButtons().add(new ArrayList<>());
                return this;
            }
            List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

            for (int i = 0; i < texts.size(); i++) {
                List<InlineKeyboardButton> column = new ArrayList<>();
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(texts.get(i));
                button.setCallbackData(callbacks.get(i));
                column.add(button);
                rowList.add(column);
            }
            rowList.forEach(row -> templates.getButtons().add(row));
            return this;
        }

        public InlineKeyboardMarkupTemplate build() {
            return templates;
        }
    }
}
