package pl.sda.demoappjdbc;

import pl.sda.demoappjdbc.config.DbConfiguration;
import pl.sda.demoappjdbc.config.DbConfigurationUtil;
import pl.sda.demoappjdbc.dao.EmployeeDao;
import pl.sda.demoappjdbc.model.Employee;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App {

    private static Connection connection;

    public static void main( String[] args ) throws SQLException {
        DbConfiguration dbConfiguration = DbConfigurationUtil.initDbConfigurationFromProperties();
        connection = DbConnectionUtil.connectToDatabase(dbConfiguration)
                .orElseThrow(() -> new RuntimeException("Unable to get connection to DB!") );
        EmployeeDao employeeDao = new EmployeeDao(connection);
        Employee employee = employeeDao.findById(10001);
        System.out.println(employee.getId());
    }
}
