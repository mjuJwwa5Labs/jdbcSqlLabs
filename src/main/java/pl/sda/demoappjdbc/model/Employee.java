package pl.sda.demoappjdbc.model;

import java.time.LocalDate;

public class Employee {
    private Integer id;
    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private EmployeeGender gender;
    private LocalDate hireDate;

    public Employee(Integer id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Employee(String firstname, String lastname, LocalDate birthDate, EmployeeGender gender, LocalDate hireDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.hireDate = hireDate;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public EmployeeGender getGender() {
        return gender;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
