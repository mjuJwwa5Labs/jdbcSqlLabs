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

        DbConfiguration dbConfiguration = DbConfigurationUtil.initDbConfigurationFromProperties();
        connection = DbConnectionUtil.connectToDatabase(dbConfiguration)
                .orElseThrow(() -> new RuntimeException("Unable to get connection do DB!"));
        EmployeeDao employeeDao = new EmployeeDao(connection);
        EmployeeService employeeService = new EmployeeService(employeeDao);

        // Tworzenie obiektu Employee na potrzeby testu (do imienia dodaje random, zeby miec pewnosc ze doda sie ten pracownik
        System.out.println("----- Tworzę nowy obiekt Employee -----");
        LocalDate birthDate = LocalDate.of(1980,9,1);
        LocalDate hireDate = LocalDate.of(2001,1,1);
        Random random = new Random();

        Employee employeeToAdd = new Employee("Imie"+random.nextInt(9999999),"Nazwisko",birthDate,EmployeeGender.M,hireDate);
        System.out.println(employeeToAdd.toString());
        Optional<Employee> employeeAddedToDb = employeeService.addEmployeeToDb(employeeToAdd);
        if (employeeAddedToDb.isPresent()) {
            System.out.println("Great success przy dodawaniu " + employeeAddedToDb.get().toString());
        } else {
            System.out.println("Nie udało mi się dodać pracownika do DB");
        }

        //Sprawdzenie czy pracownik faktycznie został dodany do DB
        System.out.println("\n----- Sprawdzam czy faktycznie dodałem tego ziutka do DB -----");
        Optional<Employee> verifiedEmployee = employeeDao.findByEmployee(employeeAddedToDb.get());
        if (verifiedEmployee.isPresent()) {
            System.out.println("Znalazłem skurczybyka w bazie " + verifiedEmployee.get().toString());
        } else {
            System.out.println("Nie znajduję tego pracownika w bazie");
        }

        //Sprawdzam czy mogę go raz jeszcze dodać, mimo że już istnieje
        System.out.println("\n----- Próbuję dodać do bazy istniejącego już pracownika -----");
       try {
            Optional<Employee> duplicatedEmployee = employeeService.addEmployeeToDb(
                    new Employee(
                            employeeToAdd.getFirstname(),
                            employeeToAdd.getLastname(),
                            employeeToAdd.getBirthDate(),
                            employeeToAdd.getGender(),
                            employeeToAdd.getHireDate()
                    )
            );
       } catch (SQLException e) {
            System.out.println("Ten pracownik już jest w bazie danych, nie możesz go baranku dodać po raz drugi");
       }
    }
}
