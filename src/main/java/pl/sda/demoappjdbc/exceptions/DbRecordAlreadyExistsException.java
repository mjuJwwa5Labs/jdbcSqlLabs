package pl.sda.demoappjdbc.exceptions;

import java.sql.SQLException;

public class DbRecordAlreadyExistsException extends SQLException {


    public DbRecordAlreadyExistsException() {
        super("Matching record alredy exists in database");
    }


    public DbRecordAlreadyExistsException(String reason) {
        super(reason);
    }
}
