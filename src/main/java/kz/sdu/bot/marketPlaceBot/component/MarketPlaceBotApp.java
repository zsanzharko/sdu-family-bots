package kz.sdu.bot.marketPlaceBot.component;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MarketPlaceBotApp extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "sdu_market_place_bot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("bots:sdu_market_place_bot");
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}