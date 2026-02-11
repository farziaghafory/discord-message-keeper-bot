package es.fplumara.dam1.messageKeeper.config;

import es.fplumara.dam1.messageKeeper.exceptions.ConfigException;
import es.fplumara.dam1.messageKeeper.model.LogMode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {
    private static final String CONFIG_FILE = "data/config.properties";
    private boolean logsEnabled;
    private LogMode logsMode;
    private String logsDir;
    private int logsMaxMessageLength;
    private Properties properties;
    public AppConfig() throws ConfigException {
        properties = new Properties();

        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {

            properties.load(fis);

            String enabledStr = properties.getProperty("logs.enabled");
            if (enabledStr == null)
                throw new ConfigException("Falta la propiedad logs.enabled");
            logsEnabled = Boolean.parseBoolean(enabledStr);

            String modeStr = properties.getProperty("logs.mode");
            if (modeStr == null)
                throw new ConfigException("Falta la propiedad logs.mode");
            logsMode = LogMode.valueOf(modeStr.toUpperCase());

            logsDir = properties.getProperty("logs.dir");
            if (logsDir == null)
                throw new ConfigException("Falta la propiedad logs.dir");

            String lengthStr = properties.getProperty("logs.maxMessageLength");
            if (lengthStr == null)
                throw new ConfigException("Missing logs.maxMessageLength property");

            try {
                logsMaxMessageLength = Integer.parseInt(lengthStr);
            } catch (NumberFormatException e) {
                logsMaxMessageLength = 500;
            }

        } catch (IOException e) {
            throw new ConfigException("Could not read config file: " + e.getMessage());
        }
    }

public boolean isLogsEnabled() {
    return logsEnabled;
}

public void setLogsEnabled(boolean logsEnabled) {
    this.logsEnabled = logsEnabled;
    properties.setProperty("logs.enabled", String.valueOf(logsEnabled));
    save();
}
    public LogMode getLogsMode() {
        return logsMode;
    }

    public String getLogsDir() {
        return logsDir;
    }

    public int getMaxMessageLength() {
        return logsMaxMessageLength;
    }

    private void save() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "Actualizado por el bot");
        } catch (IOException e){
            System.err.println("You can not save the document");
        }
    }
    }





























/*ig;








}*/
