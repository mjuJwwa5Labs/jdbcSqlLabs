package pl.sda.demoappjdbc;

import pl.sda.demoappjdbc.config.DbConfiguration;
import pl.sda.demoappjdbc.config.DbConfigurationUtil;
import pl.sda.demoappjdbc.dao.EmployeeDao;
import pl.sda.demoappjdbc.model.Employee;
import pl.sda.demoappjdbc.model.EmployeeGender;
import pl.sda.demoappjdbc.service.EmployeeService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App {

    private static Connection connection;

    public static void main(String[] args) throws SQLException {

        Random random = new Random();

        DbConfiguration dbConfiguration = DbConfigurationUtil.initDbConfigurationFromProperties();

        connection = DbConnectionUtil.connectToDatabase(dbConfiguration)
                .orElseThrow(() -> new RuntimeException("Unable to get connection do DB!"));

        EmployeeDao employeeDao = new EmployeeDao(connection);
        EmployeeService employeeService = new EmployeeService(employeeDao);


        LocalDate birthDate = LocalDate.of(1980,9,1);
        LocalDate hireDate = LocalDate.of(2001,1,1);
        Employee employeeDodawany = new Employee("Imie"+random.nextInt(9999999),"Nazwisko",birthDate,EmployeeGender.M,hireDate);

        System.out.println("----- Tworzę nowy obiekt Employee -----");
        System.out.println(employeeDodawany.toString());

        Optional<Employee> addedEmployee = employeeService.addEmployeeToDb(employeeDodawany);
        System.out.println("---- dodałem następujący employee");
        System.out.println(addedEmployee.toString());

        System.out.println("---- sprawdzam,czy ten employee został faktycznie dodany do DB:");
        Optional<Employee> employeeOptional = employeeDao.findByEmployee(addedEmployee.get());
        employeeOptional.ifPresent((empl) -> System.out.println(empl.toString()));

        System.out.println("---- sprawdzam, mogę dodać tego employee raz jeszcze");
        employeeDao.insertEmployeeByEmployee(employeeOptional.get());

    }
}
