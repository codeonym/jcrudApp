package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.ModuleModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.StudentBasicModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.StudentExtraModel;

import java.util.List;

public interface StudentExtraDao {
    public List<StudentExtraModel> getMore(List<StudentBasicModel> students, List<ModuleModel> modules) throws DaoGeneratedException;
}
