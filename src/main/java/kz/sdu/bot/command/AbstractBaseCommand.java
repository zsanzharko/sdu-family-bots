package kz.sdu.bot.command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;

public abstract class AbstractBaseCommand extends BotCommand {

    public AbstractBaseCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }
}
