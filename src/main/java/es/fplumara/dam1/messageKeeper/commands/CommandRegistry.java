package es.fplumara.dam1.messageKeeper.commands;

import es.fplumara.dam1.messageKeeper.exceptions.CommandException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    private final Map<String, BotCommand> commands = new HashMap<>();

    public void register(BotCommand command) {
        commands.put(command.name(), command);
    }

    public void handle(MessageReceivedEvent event, String rawMessage) {

        String[] parts = rawMessage.split("\\s+");
        String commandName = parts[0];

        BotCommand command = commands.get(commandName);

        if (command == null) {
            event.getChannel()
                    .sendMessage("unkown ommad : use !help")
                    .queue();
            return;
        }

        try {
            command.execute(event, parts);
        } catch (CommandException e) {
            event.getChannel()
                    .sendMessage(e.getMessage())
                    .queue();
        }
    }

    public Map<String, BotCommand> getCommands() {
        return commands;
    }
}
