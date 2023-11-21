package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {

    // GLOBAL DATABASE SETTINGS
    // THE CREDENTIALS ARE MISHANDLED JUST IN THIS EXERCISE THERE ARE BETTER WAYS TO HANDLE AUTHENTICATING --FOR SIMPLICITY
    private static final String URL = "jdbc:mariadb://localhost:3306/bd_m2i";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "master2022";

    static public  Connection  getConnection() throws SQLException {

        // THIS FUNCTION CHECKS THE CONNECTIVITY TO MYSQL SERVER [CONNECT TO SERVER MYSQL]

            try {
                // LOAD MARIADB DRIVER
                Class.forName("org.mariadb.jdbc.Driver");

                // RETURN (OBJ:CONNECTION) IF CONNECTION SUCCEEDED
                return DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }catch(ClassNotFoundException | RuntimeException | SQLException e){

                // DEBUGGING
                e.printStackTrace();
                System.out.println("\nERROR: REFUSED TO CONNECT TO DB");
            }
            // SOMETHING WENT WRONG -REFUSED TO CONNECT
        return null;
    }


    // EXTRAS - MAY BE OF USE LATTER
    public static boolean checkConnection(){
        try {
            // TESTING THE CONNECTION TO THE DATABASE
            Connection conn = DatabaseConnect.getConnection();

            // IN CASE OF SUCCESS
            return true;

        }catch(SQLException e){

            // FAILED TO CONNECT
            // PRINT THE STACK TRACE FOR DEBUGGING
            e.printStackTrace();
        }
        // IN CASE OF FAILURE
        return false;
    }

    public static void closeConnection(Connection conn){

        // EXPLICIT CLOSURE OF THE DATABASE
        try{

            // CHECK IF THE CONNECTION IS SET
            if(conn != null){

                // CLOSE THE CONNECTION
                conn.close();
            }
        }catch(SQLException e){

            // DEBUGGING
            e.printStackTrace();
        }

    }
}
