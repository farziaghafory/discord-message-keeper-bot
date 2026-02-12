package es.fplumara.dam1.messageKeeper.services.files;
import es.fplumara.dam1.messageKeeper.model.LogEntry;
import es.fplumara.dam1.messageKeeper.exceptions.StoreException;
import org.apache.commons.csv.*;
import java.io.*;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class CsvLogStore implements LogStore {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void append(Path path, LogEntry entry) throws StoreException {
        try {
            Files.createDirectories(path.getParent());
            boolean exists = Files.exists(path);
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                 CSVPrinter csv = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
                if (!exists) {
                    csv.printRecord("timestamp", "author", "content");
                }
                csv.printRecord(entry.timestamp().format(FORMAT), entry.author(), entry.content());
            }
        } catch (IOException e) {
            throw new StoreException("Error writing CSV log: " + e.getMessage());
        }
    }

    @Override
    public List<String> readAll(Path path) throws StoreException {
        if (!Files.exists(path)) return List.of();
        try (Reader reader = Files.newBufferedReader(path);
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            return parser.getRecords().stream()
                    .map(r -> r.get("timestamp") + " | " + r.get("author") + " | " + r.get("content"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new StoreException("Error reading CSV log: " + e.getMessage());
        }
    }

    @Override
    public List<String> readLast(Path path, int n) throws StoreException {
        List<String> all = readAll(path);
        int from = Math.max(all.size() - n, 0);
        return all.subList(from, all.size());
    }
}
