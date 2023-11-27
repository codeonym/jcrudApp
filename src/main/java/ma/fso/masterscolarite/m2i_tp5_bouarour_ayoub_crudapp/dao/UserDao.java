package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.UserModel;

public interface UserDao {
    public UserModel authentication(String username, String password) throws DaoGeneratedException;
}
