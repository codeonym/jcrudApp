package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;

public interface NoteDao {
    public boolean insertNote(int studentId, int moduleId, int note) throws DaoGeneratedException;
}
