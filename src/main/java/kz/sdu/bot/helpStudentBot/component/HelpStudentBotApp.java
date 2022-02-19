package kz.sdu.bot.helpStudentBot.component;

import kz.sdu.conf.HelpStudentBotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelpStudentBotApp extends TelegramLongPollingBot {

    @Autowired private HelpStudentBotConfig config;

    public HelpStudentBotApp(HelpStudentBotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return "sdu_help_student_bot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("bots:sdu_help_student_bot");
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
