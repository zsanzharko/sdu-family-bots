package kz.sdu.bot.eventsBot.component;

import kz.sdu.bot.eventsBot.component.service.EventBotMessageHandlingService;
import kz.sdu.bot.eventsBot.component.service.EventBotQueryHandlingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

//fixme Add updating event with time zone

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

        if (update.hasMessage()) {
            EventBotMessageHandlingService eventBotService = new EventBotMessageHandlingService(update);
            if (update.getMessage().hasText()) {
                //todo: create new class with thread
                switch (update.getMessage().getText().trim()) { //checking message on the default command
                    case "/events" -> eventBotService.showEvents();
                    case "/account" -> eventBotService.showAccount();
                    case "/information" -> {}
                    default -> eventBotService.defaultAction(update);
                }
            }
        } else if (update.hasCallbackQuery()) {
            EventBotQueryHandlingService eventBotService = new EventBotQueryHandlingService(update);

            String callbackData = update.getCallbackQuery().getData();

            if (callbackData.contains("/edit_account")) {
                eventBotService.editAccount();
            } else if (callbackData.contains("/liked_events_account")) {
                eventBotService.showFavoriteEvents();
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
                    eventBotService.saveEvent(idEvent);
                else if (callbackData.contains("remove")) {
                    //remove like message to account
                    eventBotService.removeEvent(idEvent);
                }
                eventBotService.editMessage(update, idEvent);
            } else if (callbackData.contains("/events_account")) {
                eventBotService.showEvents();
            }
        }
    }
}
