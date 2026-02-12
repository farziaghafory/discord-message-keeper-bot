package es.fplumara.dam1.messageKeeper.services.files;

import es.fplumara.dam1.messageKeeper.model.LogEntry;
import es.fplumara.dam1.messageKeeper.exceptions.StoreException;

import java.nio.file.Path;
import java.util.List;

public interface LogStore {
    void append(Path path, LogEntry entry) throws StoreException;

    List<String> readAll(Path path) throws StoreException;

    List<String> readLast(Path path, int n) throws StoreException;

    ;
}