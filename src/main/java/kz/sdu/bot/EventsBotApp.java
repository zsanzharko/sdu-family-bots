package kz.sdu.bot;

import kz.sdu.bot.service.EventBotService;
import kz.sdu.conf.EventBotConfig;
import kz.sdu.service.EventBotRepositoryService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//fixme Add updating event with time zone

@Slf4j
@Component
@Getter
public class EventsBotApp extends TelegramLongPollingBot {

    private static EventBotService service;

    private final EventBotRepositoryService botService;
    private final EventBotConfig config;

    @Autowired
    public EventsBotApp(EventBotRepositoryService botService, EventBotConfig config) {
        this.botService = botService;
        this.config = config;
        service = new EventBotService(botService);
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("User:\n" +
                "account id: {}", update.getMessage().getChat().getId());

        final Long telegram_id = update.getMessage().getChat().getId();
        final String telegram_chat_id = String.valueOf(update.getMessage().getChatId());


        // preprocessing
        {
            service.telegramPreprocessing(telegram_id, telegram_chat_id);
            service.setUser(botService.getAuthTelegramService().authLogUser(telegram_id, telegram_chat_id));
        }
        if (update.hasMessage()) {

            if (update.getMessage().hasText()) {
                //todo: create new class with thread
                switch (update.getMessage().getText().trim()) { //checking message on the default command
                    case "/events" -> showEvents();
                    case "/account" -> showAccount();
                    case "/information" -> {
                    }
                    default -> defaultAction(update);
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();

            if (callbackData.contains("/edit_account")) {
                editAccount(callbackData);
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
                    saveEvent(idEvent);
                else if (callbackData.contains("remove")) {
                    //remove like message to account
//                    new EventService().removeEvent(idEvent);
                    // fixme
                }
                editEventMessage(update, idEvent, callbackData);
            } else if (callbackData.contains("/events_account")) {
                showEvents();
            }
        }
    }

    public void saveEvent(Long idEvent) {
        // Todo save event to account, using some OneToOne or other.
    }

    private void defaultAction(Update update) {
        service = new EventBotService(botService);
        try {
            execute(service.defaultAction(update));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void deleteEventMessage(String chatId) {
        try {
            execute(service.deleteEventMessage(chatId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
            service.getUser().getTelegramAccount().getActivity().setLatestMessageId(null);
            log.warn("Latest message has been deleted (null), in telegramAccount {}",
                    service.getUser().getTelegramAccount().getId());
        }
    }

    private void showEvents() {
        try {
            service
                    .getUser()
                    .getTelegramAccount()
                    .getActivity()
                    .setLatestMessageId(execute(service.showEvents()).getMessageId());
            log.info("Accepted executing event. Current index - {}", 0);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Failed execute the event. Check connection or message event");
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void editEventMessage(Update update, long idEvent, String callbackData) {
        try {
            execute(service.editEventMessage(update, idEvent, callbackData));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void editAccount(String callback) {
        try {
            execute(service.editAccount(callback));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void showAccount() {
        try {
            service
                    .getUser()
                    .getTelegramAccount()
                    .getActivity()
                    .setLatestMessageId(
                            execute(service.showAccount())
                                    .getMessageId()
                    );
            log.info("Accepted executing account\n{}", service.getUser().getTelegramAccount());
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("The account could not be displayed because it was not found in" +
                    " the list of active accounts. Check list from database or array of accounts");
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
