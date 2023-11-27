package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.helpers;

public class Alert {

    // THIS CLASS HANDLES QUERIES EXECUTION STATUS
    private String message;
    private int status;

    public Alert(){
        super();
    }
    public Alert(int status, String message) {

        // CONSTRUCTING
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
