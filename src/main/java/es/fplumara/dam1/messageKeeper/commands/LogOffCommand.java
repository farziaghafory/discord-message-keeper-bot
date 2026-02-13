package es.fplumara.dam1.messageKeeper.commands;

import es.fplumara.dam1.messageKeeper.config.AppConfig;
import es.fplumara.dam1.messageKeeper.exceptions.CommandException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LogOffCommand implements BotCommand {

    private final AppConfig config;

    public LogOffCommand(AppConfig config) {
        this.config = config;
    }

    @Override
    public String name() {
        return "!log";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args)
            throws CommandException {

        if (args.length < 2 || !args[1].equalsIgnoreCase("off")) {
            return;
        }

        config.setLogsEnabled(false);
        event.getChannel()
                .sendMessage("Logging disabled")
                .queue();
    }
}

