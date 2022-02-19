package kz.sdu.bot;

import kz.sdu.conf.LostAndFoundBotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class LostAndFoundBotApp extends TelegramLongPollingBot {

    private final LostAndFoundBotConfig config;

    @Autowired
    public LostAndFoundBotApp(LostAndFoundBotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
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
