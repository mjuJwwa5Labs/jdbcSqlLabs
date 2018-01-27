package pl.sda.demoappjdbc;

import pl.sda.demoappjdbc.config.DbConfiguration;
import pl.sda.demoappjdbc.config.DbConfigurationUtil;

import java.sql.Connection;

/**
 * Hello world!
 *
 */
public class App {

    private static Connection connection;

    public static void main( String[] args ) {
        DbConfiguration dbConfiguration = DbConfigurationUtil.initDbConfigurationFromProperties();
        connection = DbConnectionUtil.connectToDatabase(dbConfiguration)
                .orElseThrow(() -> new RuntimeException("Unable to get connection to DB!") );
    }
}
