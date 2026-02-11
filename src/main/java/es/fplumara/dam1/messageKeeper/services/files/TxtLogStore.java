package es.fplumara.dam1.messageKeeper.services.files;

import es.fplumara.dam1.messageKeeper.model.LogEntry;
import es.fplumara.dam1.messageKeeper.exceptions.StoreException;

import java.io.IOException;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TxtLogStore implements LogStore {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void append(Path path, LogEntry entry) throws StoreException {
        try {
            Files.createDirectories(path.getParent());
            String line = entry.timestamp().format(DateTimeFormatter.ofPattern(String.valueOf(FORMAT) + entry.author() + entry.content())); // requires a formatter can not do format(String.valueOf(FORMAT) + entry.author() + entry.content() passing a String
            Files.writeString(path, line + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<String> readAll(Path path) throws StoreException {
        try {
            if (!Files.exists(path)) return List.of();
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new StoreException("Error al leer el registro TXT: " + e.getMessage());
        }
    }
    public  List<String> readLast(Path path, int n) throws StoreException{
        List<String> alll = readAll(path);
        int from = Math.max(alll.size() - n , 0);
        return alll.subList(from, alll.size()); //returns a view

    }

}