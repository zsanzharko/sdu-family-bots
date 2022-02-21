package kz.sdu.bot;

import kz.sdu.bot.service.EventBotService;
import kz.sdu.service.EventBotRepositoryService;
import kz.sdu.service.EventService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

//fixme Add updating event with time zone

@Slf4j
@Component
@Getter
public class EventsBotApp extends TelegramLongPollingBot {

    private final EventBotRepositoryService service;

    @Autowired
    public EventsBotApp(EventBotRepositoryService service) {
        this.service = service;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("User:\n" +
                "account id: {}", update.getMessage().getChat().getId());
        EventBotService eventBotService = new EventBotService(service);
        eventBotService.setIds(update.getMessage().getChat().getId(), update.getMessage().getChatId().toString());
        if (update.hasMessage()) {

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
            String callbackData = update.getCallbackQuery().getData();

            if (callbackData.contains("/edit_account")) {
                eventBotService.editAccount(callbackData);
//            } else if (callbackData.contains("/liked_events_account")) {
//                eventBotService.showFavoriteEvents();
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
                    new EventService().removeEvent(idEvent);
                }
                eventBotService.editEventMessage(update, idEvent, callbackData);
            } else if (callbackData.contains("/events_account")) {
                eventBotService.showEvents();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "sdu_event_bot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("bots:sdu_event_bot");
    }
}
