package kz.sdu.bot.handler;

import kz.sdu.bot.command.*;
import kz.sdu.conf.EventBotConfig;
import kz.sdu.enums.Emoji;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Getter
@Slf4j
public class EventBotCommandHandler extends CommandsHandler {

    public static final String LOGTAG = "EVENT-BOT-COMMANDS-HANDLER";

    private final EventBotConfig config;

    public EventBotCommandHandler(EventBotConfig config) {
        this.config = config;

        register(new InformationCommand());
        register(new StartCommand());
        register(new EventCommand());
        register(new StopCommand());
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);

        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(String.valueOf(message.getChatId()));
            commandUnknownMessage.setText("The command '" + message.getText() + "' is not known by this bot. Here comes some help " + Emoji.AMBULANCE);
            try {
                absSender.execute(commandUnknownMessage);
            } catch (TelegramApiException e) {
                log.error(LOGTAG, e);
            }
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
        });
    }

    @Override
    public void processNonCommandUpdate(Update update) {

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
