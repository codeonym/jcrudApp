package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.UserModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.utilities.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {

    // SQL QUERY TO CHECK IF USER IS VALID
    private final String LOGIN_USER_SQL = "SELECT username, password FROM users WHERE username=? and password=?;";

    public UserModel authentication(String username, String hashedPassword) throws SQLException {

        // TESTING DB CONNECTION
        try(Connection conn = DatabaseConnect.getConnection()) {
            if(conn != null){

                // DB CONNECTED - WILL AUTOMATICALLY CLOSE

                // PREPARING THE QUERY
                try(PreparedStatement preparedStatement = conn.prepareStatement(LOGIN_USER_SQL)){

                    // BINDING THE PARAMS
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, hashedPassword);

                    // EXECUTION
                    try(ResultSet resultSet = preparedStatement.executeQuery()){

                        // CHECK IF THERE IS A MATCH
                        if(resultSet.next()){

                            // USER AUTHENTICATED
                            // RETURNING THE USER MODEL OBJECT
                            return new UserModel(resultSet.getString("username"), resultSet.getString("password"));
                        }
                    }
                }
            }

        }catch(SQLException e){
            // AUTH FAILED: SOMETHING WENT WRONG OR DB NOT CONNECTED
            // DEBUGGING
            e.printStackTrace();
        }

        // AUTH FAILED NO USER FETCHED
        return null;
    }

}
