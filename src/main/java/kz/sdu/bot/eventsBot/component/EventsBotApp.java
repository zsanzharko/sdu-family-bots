package kz.sdu.bot.eventsBot.component;

import kz.sdu.bot.eventsBot.service.EventBotMessageHandlingService;
import kz.sdu.bot.eventsBot.service.EventBotQueryHandlingService;
import kz.sdu.bot.eventsBot.service.EventBotService;
import kz.sdu.entity.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


@Slf4j
@Component
public class EventsBotApp extends TelegramLongPollingBot {

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
        EventBotService eventBotService;

        if (update.hasMessage()) {
            eventBotService = new EventBotMessageHandlingService(update);
            if (update.getMessage().hasText()) {
                //todo: create new class with thread
                Event.updateRecentEventList(); //update events with time zone;
                switch (update.getMessage().getText().trim()) { //checking message on the default command
                    case "/events" -> eventBotService.getMessageService().showEvents();
                    case "/account" -> eventBotService.getMessageService().showAccount();
                    case "/information" -> {}
                    default -> eventBotService.getMessageService().defaultAction(update);
                }
            }
        } else if (update.hasCallbackQuery()) {
            eventBotService = new EventBotQueryHandlingService(update);
            String callbackData = update.getCallbackQuery().getData();

            if (callbackData.contains("/edit_account")) {
                eventBotService.getQueryHandlingService().editAccount();
            } else if (callbackData.contains("/liked_events_account")) {
                eventBotService.getQueryHandlingService().showFavoriteEvents();
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
                    eventBotService.getQueryHandlingService().saveEvent(idEvent);
                else if (callbackData.contains("remove")) {
                    //remove like message to account
                    eventBotService.getQueryHandlingService().removeEvent(idEvent);
                }
                eventBotService.getQueryHandlingService().editMessage(update, idEvent);
            } else if (callbackData.contains("/events_account")) {
                eventBotService.getQueryHandlingService().showEvents();
            }
        }
    }
}
