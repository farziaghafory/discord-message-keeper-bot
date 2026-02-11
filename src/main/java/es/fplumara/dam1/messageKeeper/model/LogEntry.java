package es.fplumara.dam1.messageKeeper.model;

import java.io.Console;
import java.time.LocalDateTime;

public record LogEntry(LocalDateTime timestamp, String author, String content, String channelId){

}