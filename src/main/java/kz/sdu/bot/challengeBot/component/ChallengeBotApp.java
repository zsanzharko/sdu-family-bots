package kz.sdu.bot.challengeBot.component;

import kz.sdu.conf.ChallengeBotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ChallengeBotApp extends TelegramLongPollingBot {

    private final ChallengeBotConfig config;

    public ChallengeBotApp(ChallengeBotConfig config) {
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

    }
}
