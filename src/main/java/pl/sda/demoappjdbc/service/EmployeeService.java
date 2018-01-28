package pl.sda.demoappjdbc.service;

import pl.sda.demoappjdbc.dao.EmployeeDao;
import pl.sda.demoappjdbc.exceptions.DbRecordAlreadyExistsException;
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
        Optional<Employee> validatedEmployee = validateIfEmployeeExists(employee);

        if (validatedEmployee.isPresent()) {
//            throw new SQLException("This user already exists in the DB");
            throw new DbRecordAlreadyExistsException();
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

    public Optional<Employee> validateIfEmployeeExists(Employee employee) throws SQLException {
        Optional<Employee> validatedEmployee = employeeDao.findByEmployee(employee);
        return validatedEmployee;
    }

    public int incrementEmployeeId(int employeeMaxId) {
        return ++employeeMaxId;
    }

}
