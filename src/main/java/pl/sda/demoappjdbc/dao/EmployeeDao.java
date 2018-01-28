package pl.sda.demoappjdbc.dao;

import com.sun.istack.internal.NotNull;
import pl.sda.demoappjdbc.model.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class EmployeeDao {

    public static final String FIND_EMPLOYEE_BY_ID_QUERY = "select * from employees where emp_no=?";
    public static final String FIND_EMPLOYEE_BY_FIRSTNAME_AND_LASTNAME = "select * from employees where first_name like ? and last_name like ?";
    public static final String FIND_MAX_EMPLOYEE_ID = "select max(emp_no) from employees";
    public static final String ADD_EMPLOYEE_BY_EMPLOYEE=
            "insert into employees (emp_no, birth_date, first_name, last_name, gender, hire_date) " +
                    "values(?, ?, ?, ?, ?, ?)";



    private Connection connection;

    public EmployeeDao(Connection connection) {
        this.connection = connection;
    }

    public Employee findById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_EMPLOYEE_BY_ID_QUERY);
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

    public Optional<Employee> findByEmployee(Employee employee) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(FIND_EMPLOYEE_BY_FIRSTNAME_AND_LASTNAME);
        preparedStatement.setString(1, employee.getFirstname());
        preparedStatement.setString(2, employee.getLastname());
        ResultSet resultSet = preparedStatement.executeQuery();

        Optional<Employee> employeeOptional = Optional.empty();
        while (resultSet.next()) {
            employeeOptional = Optional.of(new Employee(
                    resultSet.getInt("emp_no"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name")));
        }

        return employeeOptional;
    }

    public int findMaxEmployeeNumber() throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_MAX_EMPLOYEE_ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        int employeeId = -1;

        while (resultSet.next()) {
            employeeId = resultSet.getInt("max(emp_no)");
        }

        return employeeId;
    }

    public Employee insertEmployeeByEmployee(Employee employee) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_EMPLOYEE_BY_EMPLOYEE);
        preparedStatement.setString(1, String.valueOf(employee.getId()));
        preparedStatement.setString(2, employee.getBirthDate().toString());
        preparedStatement.setString(3, employee.getFirstname());
        preparedStatement.setString(4, employee.getLastname());
        preparedStatement.setString(5, employee.getGender().toString());
        preparedStatement.setString(6, employee.getHireDate().toString());
        preparedStatement.executeUpdate();

        return employee;
    }

}
