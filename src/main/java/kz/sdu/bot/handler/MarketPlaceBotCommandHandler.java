package kz.sdu.bot.handler;

import kz.sdu.bot.MarketPlaceBotApp;
import kz.sdu.conf.MarketPlaceBotConfig;
import kz.sdu.service.MarketPlaceBotService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class MarketPlaceBotCommandHandler extends MarketPlaceBotApp {

    public static final String LOGTAG = "MP-BOT-COMMAND-HANDLER";

    private final MarketPlaceBotService service;

    public MarketPlaceBotCommandHandler(MarketPlaceBotConfig config, MarketPlaceBotService marketPlaceBotService) {
        super(config, marketPlaceBotService);
        this.service = marketPlaceBotService;
    }

    public void commandHandler(String command, Update update) {

        try {
            switch (command) {
                case "/information" -> informationHandler(update);
                case "/stop" -> stopHandler(update);
                case "/market" -> marketHandler(update);
                default -> log.error("Command is wrong, check it");
            }
        } catch (TelegramApiException e) {
            log.error(LOGTAG, e);
        }
    }

    private void informationHandler(Update update) throws TelegramApiException {
        final String information = """
                This bot will show products who want to sell""";

        execute(new SendMessage(
                update.getMessage().getChatId().toString(),
                information));
    }

    private void stopHandler(Update update) throws TelegramApiException {
        final String information = """
                Your account has stopped. Next action with using family bots will check account again
                Please take this into account!""";

        // fixme do stopping account in telegram

        execute(new SendMessage(
                update.getMessage().getChatId().toString(),
                information));
    }

    private void marketHandler(Update update) {

    }
}
