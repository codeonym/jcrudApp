package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.Factory;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao.*;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DatabaseException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {

    // DEFAULT DATABASE SETTINGS
    private static final String URL = "jdbc:mariadb://localhost:3306/bd_m2i";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    // DECLARATIONS
    private String url;
    private String username;
    private String password;

    DaoFactory(String url, String username, String password) {

        // CONSTRUCTING
        this.url = url;
        this.username = username;
        this.password = password;
    }
    public static DaoFactory getInstance() throws DatabaseException {

        try {

            // LOAD MARIADB DRIVER
            Class.forName("org.mariadb.jdbc.Driver");

            // INSTANCING THE DAO FACTORY
            DaoFactory instance = new DaoFactory(URL, USERNAME, PASSWORD);

            // RETURN INSTANCE
            return instance;

        } catch (ClassNotFoundException e) {

            // FAILED TO GET INSTANCE
            // DEBUGGING
            e.printStackTrace();

            // THROW AN EXCEPTION
            throw new DatabaseException("Failed To Connect To Database \n" + e.getMessage());
        }

    }
    public Connection getConnection() throws DatabaseException {

        try {
            // ESTABLISHING A CONNECTION TO THE DATABASE
            return DriverManager.getConnection(url, username, password);
        }catch (SQLException e) {

            // FAILED TO CONNECT
            // DEBUGGING
            e.printStackTrace();

            // THROW AN EXCEPTION
            throw new DatabaseException("Failed To Connect To Database \n" + e.getMessage());
        }
    }

    // DAO RECUPERATION
    public UserDao getUserDao() {
        return new UserAuthService(this);
    }
    public StudentBasicDao getStudentBasicDao() {
        return new StudentBasicService(this);
    }
    public StudentExtraDao getStudentExtraDao() {
        return new StudentExtraService(this);
    }
    public ModuleDao getModuleDao() {
        return new ModuleService(this);
    }
    public ModuleStatsDao getModuleStatsDao() {
        return new ModuleStatsService(this);
    }
    public AbsenceDao getAbsenceDao() {
        return new StudentBasicService(this);
    }
    public NoteDao getNoteDao() {
        return new StudentBasicService(this);
    }

    // EXTRAS - MAY BE OF USE LATTER
    public boolean checkConnection() {
        try {
            // TESTING THE CONNECTION TO THE DATABASE
            Connection conn = getConnection();

            // IN CASE OF SUCCESS
            return true;

        }catch(SQLException e){
            // DO NOTHING
        }

        // CONNECTION FAILED
        return false;
    }
    public static void closeConnection(Connection conn) throws DatabaseException {

        // EXPLICIT CLOSING OF THE DATABASE
        try{

            // CHECK IF THE CONNECTION IS SET
            if(conn != null){

                // CLOSE THE CONNECTION
                conn.close();
            }
        }catch(SQLException e){

            // DEBUGGING
            e.printStackTrace();

            throw new DatabaseException("Failed To Close The Database!");
        }

    }
}