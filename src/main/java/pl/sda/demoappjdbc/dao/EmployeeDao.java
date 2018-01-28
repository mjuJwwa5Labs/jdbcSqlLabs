package pl.sda.demoappjdbc.dao;

import pl.sda.demoappjdbc.model.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class EmployeeDao {

    public static final String FIND_EMPLOYEE_BY_ID_QUERY = "select * from employees where emp_no=?";
    public static final String FIND_EMPLOYEE_BY_EMPLOYEE_OBJECT = "select * from employees where first_name=? and last_name=?";
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

    //TODO - piszemy metodę, która pozwoli nam dodać nowego Employee do DB
    //
    public Optional<Employee> findByEmployee(Employee employee) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_EMPLOYEE_BY_EMPLOYEE_OBJECT);
        preparedStatement.setString(1, employee.getFirstname());
        preparedStatement.setString(2, employee.getLastname());
        ResultSet resultSet = preparedStatement.executeQuery();

        Optional<Employee> employeeOptional = null;

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
        preparedStatement.setString(2, formatDateAsString(employee.getBirthDate()));
        preparedStatement.setString(3, employee.getFirstname());
        preparedStatement.setString(4, employee.getLastname());
        preparedStatement.setString(5, employee.getGender().toString());
        preparedStatement.setString(6, formatDateAsString(employee.getHireDate()));
        preparedStatement.executeUpdate();

        return employee;
    }

    private String formatDateAsString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
