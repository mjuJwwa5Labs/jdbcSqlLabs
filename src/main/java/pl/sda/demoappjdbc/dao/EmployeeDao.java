package pl.sda.demoappjdbc.dao;

import pl.sda.demoappjdbc.model.Employee;

import java.sql.*;

public class EmployeeDao {

    private Connection connection;
    private static final String FIND_BY_ID_QUERY="select * from employees where emp_no=?";

    public EmployeeDao(Connection connection) {
        this.connection = connection;
    }

    public Employee findById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();

        Employee employee = null;

        while (resultSet.next()) {
            employee = new Employee(
                    resultSet.getInt("emp_no"),
                    resultSet.getString("first_name"),
                    resultSet.getString("first_last"));
        }
        return employee;
    }
}
