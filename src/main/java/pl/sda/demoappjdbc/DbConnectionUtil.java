package pl.sda.demoappjdbc;

import org.apache.poi.hssf.record.DBCellRecord;
import pl.sda.demoappjdbc.config.DbConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public final class DbConnectionUtil {

    public static Optional<Connection> connectToDatabase(DbConfiguration dbConfiguration) {
        Optional<Connection> connection = null;

        registerDbDriver(dbConfiguration);
        try {
            connection = connect(dbConfiguration);
        } catch (SQLException e) {
            System.out.println("Can't connect to the DB with message:\n " + e.getMessage());
        }
        return connection;
    }

    private static Optional<Connection> connect(DbConfiguration dbConfiguration) throws SQLException {
        String url = buildConnctionString(dbConfiguration);
        return Optional.of(DriverManager.getConnection(
                url,
                dbConfiguration.getUser(),
                dbConfiguration.getPassword()));
    }

    private static String buildConnctionString(DbConfiguration dbConfiguration) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(dbConfiguration.getUrl())
                .append("/")
                .append(dbConfiguration.getDbName())
                .append("?")
                .append(dbConfiguration.getUseSSL())
                .append("&")
                .append(dbConfiguration.getTimeZone());

        return stringBuilder.toString();
    }

    private static void registerDbDriver(DbConfiguration dbConfiguration) {
        try {
            Class.forName(dbConfiguration.getDriver());
        } catch (ClassNotFoundException e) {
            System.out.println("Error while driver registration: " + e.getMessage());
        }
    }
}
