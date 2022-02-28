package kz.sdu.bot;

import kz.sdu.conf.HelpStudentBotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelpStudentBotApp extends TelegramLongPollingBot {

    private final HelpStudentBotConfig config;

    @Autowired
    public HelpStudentBotApp(HelpStudentBotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
