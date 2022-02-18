package kz.sdu.bot.marketPlaceBot.component;

import kz.sdu.conf.MarketPlaceBotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MarketPlaceBotApp extends TelegramLongPollingBot {

    @Autowired private MarketPlaceBotConfig config;

    public MarketPlaceBotApp(MarketPlaceBotConfig config) {
        this.config = config;
    }

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
