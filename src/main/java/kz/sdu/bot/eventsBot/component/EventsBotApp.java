package kz.sdu.bot.eventsBot.component;

import kz.sdu.bot.entity.Event;
import kz.sdu.bot.eventsBot.service.EventBotQueryHandlingService;
import kz.sdu.bot.eventsBot.service.EventBotService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


@Slf4j
@Component
public class EventsBotApp extends TelegramLongPollingBot {

    final Logger logger = LoggerFactory.getLogger(EventsBotApp.class);

    @Override
    public String getBotUsername() {
        return "sdu_event_bot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("bots:sdu_event_bot");
    }

    @Override
    public void onUpdateReceived(Update update) {
        final EventBotService eventBotService = new EventBotService();
        final EventBotQueryHandlingService queryHandlingService = new EventBotQueryHandlingService();

        if (update.hasMessage()) {
            eventBotService.setUpdate(update);
            if (update.getMessage().hasText()) {
                //todo: create new class with thread
                Event.updateRecentEventList(); //update events with time zone;
                switch (update.getMessage().getText().trim()) { //checking message on the default command
                    case "/events" -> eventBotService.showEvents();
                    case "/account" -> eventBotService.showAccount();
                    case "/information" -> {}
                    default -> eventBotService.defaultAction(update);
                }
            }
        } else if (update.hasCallbackQuery()) {

            String callbackData = update.getCallbackQuery().getData();

            queryHandlingService.setUpdate(update);
            if (callbackData.contains("/edit_account")) {
                queryHandlingService.editAccount();
            } else if (callbackData.contains("/liked_events_account")) {
                queryHandlingService.showFavoriteEvents();
            } else if (callbackData.contains("_like_event")) {
                // При нажатии кнопки лайка или дизлайк, сообщение должен изменится
                // кнопка на противоположную сторону

                final long idEvent;

                if (callbackData.contains("account")) {
                    idEvent = Long.parseLong(
                            callbackData.substring(callbackData.indexOf("&id=") + 4));
                } else idEvent = Long.parseLong(callbackData.substring(
                        callbackData.indexOf("&id=") + 4,
                        callbackData.indexOf("&index"))); // get event id in callback

                if (callbackData.contains("save"))
                    //save like message to account
                    queryHandlingService.saveEvent(idEvent);
                else if (callbackData.contains("remove")) {
                    //remove like message to account
                    queryHandlingService.removeEvent(idEvent);
                }
                queryHandlingService.editMessage(update, idEvent);
            } else if (callbackData.contains("/events_account")) {
                queryHandlingService.showEvents();
            }
        }
    }
}
