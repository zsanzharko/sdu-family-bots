package kz.sdu.bot.helpStudentBot.component;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelpStudentBotApp extends TelegramLongPollingBot {
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
