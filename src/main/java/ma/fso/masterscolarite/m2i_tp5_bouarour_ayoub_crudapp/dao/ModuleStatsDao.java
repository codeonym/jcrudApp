package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.ModuleStatModel;

public interface ModuleStatsDao {
    public ModuleStatModel get(int studentId, String studentCne, int moduleId) throws DaoGeneratedException;
}
