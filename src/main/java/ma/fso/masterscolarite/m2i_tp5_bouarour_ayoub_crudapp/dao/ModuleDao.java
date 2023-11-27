package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.ModuleModel;

import java.sql.SQLException;
import java.util.List;

public interface ModuleDao {
    public List<ModuleModel> fetchAll() throws DaoGeneratedException;
}
