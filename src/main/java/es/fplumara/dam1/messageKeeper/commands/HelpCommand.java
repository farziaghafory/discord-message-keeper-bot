package es.fplumara.dam1.messageKeeper.commands;

import es.fplumara.dam1.messageKeeper.commands.BotCommand;
import es.fplumara.dam1.messageKeeper.commands.CommandRegistry;
import es.fplumara.dam1.messageKeeper.exceptions.CommandException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand implements BotCommand {

    private final CommandRegistry registry;

    public HelpCommand(CommandRegistry registry) {
        this.registry = registry;
    }

    @Override
    public String name() {
        return "!help";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args)
            throws CommandException {

        StringBuilder builder = new StringBuilder();
        builder.append("Available commands:");

        registry.getCommands()
                .keySet()
                .forEach(cmd -> builder.append(cmd).append("\n"));

        event.getChannel()
                .sendMessage(builder.toString())
                .queue();
    }
}

