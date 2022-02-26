package kz.sdu.bot;

import kz.sdu.conf.MarketPlaceBotConfig;
import kz.sdu.service.MarketPlaceBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
public class MarketPlaceBotApp extends TelegramLongPollingBot {

    private final MarketPlaceBotConfig config;
    private final MarketPlaceBotService marketPlaceBotService;

    @Autowired
    public MarketPlaceBotApp(MarketPlaceBotConfig config, MarketPlaceBotService marketPlaceBotService) {
        this.config = config;
        this.marketPlaceBotService = marketPlaceBotService;
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
            Message message = update.getMessage();

            final User telegram_user = message.getFrom();
            final String chatId = String.valueOf(message.getChatId());

            kz.sdu.entity.User user = marketPlaceBotService.authUser(update);

            if (commandHandler(message.getText())) {

            }


        }
    }

    private boolean commandHandler(String command) {
        return command.startsWith("/");
    }
}
