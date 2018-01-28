package pl.sda.demoappjdbc.dao;

import pl.sda.demoappjdbc.model.Employee;

import java.sql.*;

public class EmployeeDao {

    public static final String FIND_BY_ID_QUERY = "select * from employees where emp_no=?";

    private Connection connection;

    public EmployeeDao(Connection connection) {
        this.connection = connection;
    }

    public Employee findById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        Employee employee = null;
        while (resultSet.next()) {
            employee = new Employee(
                    resultSet.getInt("emp_no"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"));
        }

        return employee;
    }
}
