package kz.sdu.sdubotsfamily;

import kz.sdu.bot.challengeBot.component.ChallengeBotApp;
import kz.sdu.entity.Event;
import kz.sdu.bot.eventsBot.component.EventsBotApp;
import kz.sdu.bot.helpStudentBot.component.HelpStudentBotApp;
import kz.sdu.bot.lostAndFoundBot.LostAndFoundBotApp;
import kz.sdu.bot.marketPlaceBot.component.MarketPlaceBotApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class SduBotsFamilyApplication {

    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2022, 2, 13);
        LocalTime time = LocalTime.of(6, 31);
        Event.getEventList().add(new Event(new File("src/main/resources/images/default_blog_image.jpg"), "Elephant", "There are times when you'd prefer to do things without " +
                "sending any messages to the chat. For example, when your user is changing settings or flipping " +
                "through search results. In such cases you can use Inline Keyboards that are integrated directly " +
                "into the messages they belong to.", false, 300));
        Event.getEventList().get(0).setTimeEvent(date, time);
        date = LocalDate.of(2022, 2, 20);
        time = LocalTime.of(6, 31);
        Event.getEventList().add(new Event(new File("src/main/resources/images/default_blog_image.jpg"), "Elephant", "There are times when you'd prefer to do things without " +
                "sending any messages to the chat. For example, when your user is changing settings or flipping " +
                "through search results. In such cases you can use Inline Keyboards that are integrated directly " +
                "into the messages they belong to.", false, 100));
        Event.getEventList().get(1).setTimeEvent(date, time);

        SpringApplication.run(SduBotsFamilyApplication.class, args);

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new EventsBotApp());
            telegramBotsApi.registerBot(new ChallengeBotApp());
            telegramBotsApi.registerBot(new HelpStudentBotApp());
            telegramBotsApi.registerBot(new MarketPlaceBotApp());
            telegramBotsApi.registerBot(new LostAndFoundBotApp());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
