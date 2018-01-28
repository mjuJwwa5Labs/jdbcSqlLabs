package pl.sda.demoappjdbc;

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
            connection = Optional.of(DriverManager.getConnection(
                        buildConnctionString(dbConfiguration),
                        dbConfiguration.getUser(),
                        dbConfiguration.getPassword()));
            //connection = Optional.of(DriverManager.getConnection("jdbc:mysql://localhost:3306/employees?useSSL=false", "root", "M@rek"));
        } catch (SQLException e) {
            System.out.println("Can't connect to the DB with message:\n " + e.getMessage());
        }
        return connection;
    }

    private static String buildConnctionString(DbConfiguration dbConfiguration) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(dbConfiguration.getUrl());
        stringBuilder.append("/");
        stringBuilder.append(dbConfiguration.getDbName());
        stringBuilder.append("?");
        stringBuilder.append(dbConfiguration.getUseSSL());
        stringBuilder.append("&");
        stringBuilder.append(dbConfiguration.getTimeZone());

        return stringBuilder.toString();
    }

    private static void registerDbDriver(DbConfiguration dbConfiguration) {
        try {
            Class.forName(dbConfiguration.getDriver());
        } catch (ClassNotFoundException e) {
            System.out.println("Nie mogę zarejestrować drivera");
        }
    }
}
