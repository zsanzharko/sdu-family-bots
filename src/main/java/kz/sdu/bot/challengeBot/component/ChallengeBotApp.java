package kz.sdu.bot.challengeBot.component;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ChallengeBotApp extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "sdu_challenge_bot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("bots:sdu_challenge_bot");
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
