package kz.sdu.bot.lostAndFoundBot.component;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class LostAndFoundBotApp extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "sdu_lost_and_found_bot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("bots:sdu_lost_and_found_bot");
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
