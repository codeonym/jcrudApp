package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;

public interface AbsenceDao {
    public boolean insertAbsence(int studentId, int moduleId, String absenceDate) throws DaoGeneratedException;
}
