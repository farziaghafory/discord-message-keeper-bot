package es.fplumara.dam1.messageKeeper.commands;

import es.fplumara.dam1.messageKeeper.exceptions.CommandException;
import es.fplumara.dam1.messageKeeper.services.files.DefaultFileService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class LastCommand implements BotCommand {

    private final DefaultFileService fileService;

    public LastCommand(DefaultFileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public String name() {
        return "!last";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args)
            throws CommandException {

        if (args.length < 2) {
            throw new CommandException("Usage:!last n");
        }

        int n;

        try {
            n = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new CommandException("n must be a number");
        }

        String channelId = event.getChannel().getId();

        try {
            List<String> lines =
                    fileService.readLast(channelId, n);

            if (lines.isEmpty()) {
                event.getChannel()
                        .sendMessage("No history")
                        .queue();
                return;
            }

            String response = String.join("\n", lines);

            if (response.length() > 1900) {
                response = response.substring(0, 1900)
                        + ("truncated");
            }

            event.getChannel()
                    .sendMessage(" " + response + " ")
                    .queue();

        } catch (Exception e) {
            throw new CommandException("Error reading history",e);
        }
    }
}

