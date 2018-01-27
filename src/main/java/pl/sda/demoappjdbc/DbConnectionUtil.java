package pl.sda.demoappjdbc;

import pl.sda.demoappjdbc.config.DbConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DbConnectionUtil {

    public static Optional<Connection> connectToDatabase(DbConfiguration dbConfiguration) {
        Optional<Connection> connection = null;
        registerDbDriver(dbConfiguration);
        try {
            //TODO przeanalizować to połączenie
            connection = Optional.of(DriverManager.getConnection(
                    dbConfiguration.getUrl() + "?" + dbConfiguration.getUseSSL()
                    + "/" + dbConfiguration.getDbName(), dbConfiguration.getUser(), dbConfiguration.getPassword()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
            //log exception
        return connection;
    }

    private static void registerDbDriver(DbConfiguration dbConfiguration) {
        try {
            Class.forName(dbConfiguration.getDriver());
        } catch (ClassNotFoundException e) {
            // log exception
        }
    }

}
