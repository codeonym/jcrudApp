package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.Factory.DaoFactory;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao.UserDao;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthService implements UserDao{

    // SQL QUERY TO CHECK IF USER IS VALID
    private final String LOGIN_USER_SQL = "SELECT username, password FROM users WHERE username=? and password=?;";
    private DaoFactory daoFactory;

    public UserAuthService( DaoFactory daoFactory) {

        // CONSTRUCTING
        this.daoFactory = daoFactory;
    }
    @Override
    public UserModel authentication(String username, String password) throws DaoGeneratedException {

        // TESTING DB CONNECTION
        try(Connection conn = daoFactory.getConnection()) {

            if(conn != null){

                // DB CONNECTED - WILL AUTOMATICALLY CLOSE

                // PREPARING THE QUERY
                try(PreparedStatement preparedStatement = conn.prepareStatement(LOGIN_USER_SQL)){

                    // BINDING THE PARAMS
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

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
            throw new DaoGeneratedException("SOMETHING WENT WRONG -- AUTHENTICATION FAILED");
        }

        // AUTH FAILED NO USER FETCHED
        return null;
    }
}
