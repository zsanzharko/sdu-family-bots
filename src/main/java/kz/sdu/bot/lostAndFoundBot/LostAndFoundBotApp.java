package kz.sdu.bot.lostAndFoundBot;

import kz.sdu.conf.LostAndFoundBotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class LostAndFoundBotApp extends TelegramLongPollingBot {

    @Autowired LostAndFoundBotConfig config;

    public LostAndFoundBotApp(LostAndFoundBotConfig config) {
        this.config = config;
    }

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

        if (update.hasMessage()) {

            if (update.getMessage().hasText()) {

                switch (update.getMessage().getText().trim()) {
                    case "/lost" -> {}
                    case "/account" -> {}
                    case "/information" -> {}
                    default -> {}
                }
            }
        }
    }
}
