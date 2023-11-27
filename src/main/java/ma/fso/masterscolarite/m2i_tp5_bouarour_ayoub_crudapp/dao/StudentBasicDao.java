package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.StudentBasicModel;

import java.sql.SQLException;
import java.util.List;

public interface StudentBasicDao {
    public List<StudentBasicModel> fetchAll() throws DaoGeneratedException;
    public StudentBasicModel search(String cne) throws DaoGeneratedException;
    public boolean insert(StudentBasicModel student) throws DaoGeneratedException;
    public boolean update(StudentBasicModel student) throws DaoGeneratedException;
    public boolean delete(String cne) throws DaoGeneratedException;
}
