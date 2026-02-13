package es.fplumara.dam1.messageKeeper.commands;

import es.fplumara.dam1.messageKeeper.exceptions.CommandException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface BotCommand {

    String name();

    void execute(MessageReceivedEvent event, String[] args)
            throws CommandException;
}
