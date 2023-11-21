package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models;

public class UserModel {

    // DECLARING THE ATTRS FOR USER MODEL
    private String username;
    private String password;

    public UserModel(){

        // ADDING DEFAULT CONSTRUCTOR TO STICK TO THE JAVABEANS REQUIREMENTS
        super();
    }
    public UserModel(String username, String password){

        // CONSTRUCTING THE USER MODEL WITH GIVEN DATA
        this.username = username;
        this.password = password;
    }

    // GETTERS AND SETTERS
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
