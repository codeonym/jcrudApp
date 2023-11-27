package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions;

public class DaoGeneratedException extends Exception {

    private String message = "Something Went Wrong!";
    public DaoGeneratedException(String message){

        this.message = message;
    }

    public String getMessage() {
        return "DAO EXCEPTION: " + message;
    }
}
