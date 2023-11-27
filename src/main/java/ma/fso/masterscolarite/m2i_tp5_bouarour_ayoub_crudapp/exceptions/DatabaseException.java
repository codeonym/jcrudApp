package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions;

import java.sql.SQLException;

public class DatabaseException extends SQLException {

    private String message = "Something Went Wrong!";
    public DatabaseException(String message){

        this.message = message;
    }

    public String getMessage() {
        return "DATABASE EXCEPTION: " + message;
    }
}
