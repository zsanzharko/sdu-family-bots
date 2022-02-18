package kz.sdu.bot.eventsBot.component;

import kz.sdu.bot.eventsBot.component.service.EventBotService;
import kz.sdu.conf.EventBotConfig;
import kz.sdu.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

//fixme Add updating event with time zone

@Slf4j
@Service
public class EventsBotApp extends TelegramLongPollingBot {

    @Autowired final EventBotConfig config;

    public EventsBotApp(EventBotConfig config) {
        this.config = config;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("User:\n" +
                "account id: {}", update.getMessage().getChat().getId());
        EventBotService eventBotService = new EventBotService(config);
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
        return config.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
