package pl.sda.demoappjdbc.service;

import pl.sda.demoappjdbc.dao.EmployeeDao;
import pl.sda.demoappjdbc.model.Employee;

import java.sql.SQLException;
import java.util.Optional;

public class EmployeeService {

    EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public Optional<Employee> addEmployeeToDb(Employee employee) throws SQLException {

        Optional<Employee> insertedEmployeeOptional = null;

        if (validateIfEmployeeExists(employee).isPresent()) {
            throw new SQLException("This user already exists in the DB");
        }

        Employee employeeToBeInserted = employee;
        employeeToBeInserted = getMaxIdFromDbAndSetEmployeeId(employeeToBeInserted);
        insertedEmployeeOptional = Optional.of(employeeDao.insertEmployeeByEmployee(employeeToBeInserted));

        return insertedEmployeeOptional;
    }

    private Employee getMaxIdFromDbAndSetEmployeeId(Employee employee) throws SQLException {

        Employee updatedEmployee = employee;

        int employeeToIncrement = employeeDao.findMaxEmployeeNumber();
        employeeToIncrement = incrementEmployeeId(employeeToIncrement);
        updatedEmployee.setId(employeeToIncrement);
        return updatedEmployee;
    }

    private Optional<Employee> validateIfEmployeeExists(Employee employee) throws SQLException {
        Optional<Employee> validatedEmployee = employeeDao.findByEmployee(employee);
        return validatedEmployee;
    }

    //TODO ta metoda pójdzie do DAO
    public int incrementEmployeeId(int employeeMaxId) {
        return ++employeeMaxId;
    }

}
