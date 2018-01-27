package pl.sda.demoappjdbc.model;

public class Employee {

    private Integer id;
    private String firstname;
    private String lastname;

    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Employee(Integer id, String firstname, String lastname) {

        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
