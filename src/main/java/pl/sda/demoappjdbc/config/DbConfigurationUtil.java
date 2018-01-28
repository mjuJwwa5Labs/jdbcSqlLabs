package pl.sda.demoappjdbc.config;

import pl.sda.demoappjdbc.App;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class DbConfigurationUtil {

    private static final String DB_CONFIGURATION_PROPERTIES_FILE = "dbConnection.properties";

    public static DbConfiguration initDbConfigurationFromProperties() {
        Properties properties = createProperties();
        DbConfiguration dbConfiguration = new DbConfiguration(
                properties.getProperty("jdbc.driver"),
                properties.getProperty("db.url"),
                properties.getProperty("db.name"),
                properties.getProperty("db.useSSL"),
                properties.getProperty("db.timeZone"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password"));

        return dbConfiguration;
    }

    private static final Properties createProperties() {
        Properties properties = null;

        try (InputStream inputStream = App.class.getClassLoader()
                .getResourceAsStream(DB_CONFIGURATION_PROPERTIES_FILE)) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            // TODO log error
        } catch (IOException e) {
            // TODO log error
        }

        return properties;
    }
}
