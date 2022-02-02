package kz.sdu.sdubotsfamily;

import kz.sdu.bot.challengeBot.component.ChallengeBotApp;
import kz.sdu.bot.eventsBot.component.EventsBotApp;
import kz.sdu.bot.helpStudentBot.component.HelpStudentBotApp;
import kz.sdu.bot.marketPlaceBot.component.MerketPlaceBotApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class SduBotsFamilyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SduBotsFamilyApplication.class, args);

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new EventsBotApp());
            telegramBotsApi.registerBot(new ChallengeBotApp());
            telegramBotsApi.registerBot(new HelpStudentBotApp());
            telegramBotsApi.registerBot(new MerketPlaceBotApp());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
