package kz.sdu.sdubotsfamily;

import kz.sdu.bot.challengeBot.component.ChallengeBotApp;
import kz.sdu.bot.eventsBot.component.EventsBotApp;
import kz.sdu.bot.helpStudentBot.component.HelpStudentBotApp;
import kz.sdu.bot.lostAndFoundBot.LostAndFoundBotApp;
import kz.sdu.bot.marketPlaceBot.component.MarketPlaceBotApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Slf4j
public class BotsInitializer {

    private final EventsBotApp eventsBotApp;
    private final ChallengeBotApp challengeBotApp;
    private final HelpStudentBotApp helpStudentBotApp;
    private final LostAndFoundBotApp lostAndFoundBotApp;
    private final MarketPlaceBotApp marketPlaceBotApp;

    @Autowired
    public BotsInitializer(EventsBotApp eventsBotApp, ChallengeBotApp challengeBotApp, HelpStudentBotApp helpStudentBotApp, LostAndFoundBotApp lostAndFoundBotApp, MarketPlaceBotApp marketPlaceBotApp) {
        this.eventsBotApp = eventsBotApp;
        this.challengeBotApp = challengeBotApp;
        this.helpStudentBotApp = helpStudentBotApp;
        this.lostAndFoundBotApp = lostAndFoundBotApp;
        this.marketPlaceBotApp = marketPlaceBotApp;
    }


    @EventListener({ContextRefreshedEvent.class})
    public void Init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(eventsBotApp);
            telegramBotsApi.registerBot(challengeBotApp);
            telegramBotsApi.registerBot(helpStudentBotApp);
            telegramBotsApi.registerBot(lostAndFoundBotApp);
            telegramBotsApi.registerBot(marketPlaceBotApp);
        } catch (TelegramApiRequestException e) {
            log.error(e.getMessage());
        }
    }
}
