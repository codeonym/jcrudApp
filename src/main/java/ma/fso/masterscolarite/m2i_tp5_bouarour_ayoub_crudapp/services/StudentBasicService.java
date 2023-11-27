package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.Factory.DaoFactory;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao.AbsenceDao;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao.NoteDao;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao.StudentBasicDao;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.StudentBasicModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBasicService implements StudentBasicDao, NoteDao, AbsenceDao {

    // SQL QUERIES
    private final String INSERT_STUDENT_SQL = "INSERT INTO etudiants (nom, prenom, date_naissance, cne, adresse) VALUES (?, ?, ?, ?, ?);";
    private final String UPDATE_STUDENT_SQL = "UPDATE etudiants SET nom=?, prenom=?, date_naissance=?, adresse=? WHERE cne=?;";
    private final String DELETE_STUDENT_SQL = "DELETE FROM etudiants WHERE cne=?;";
    private final String SELECT_STUDENTS_SQL = "SELECT et.etudiant_id AS id, et.nom, et.prenom, et.cne, et.date_naissance AS dateNaissance, et.adresse FROM etudiants et;";
    private final String SELECT_STUDENT_SQL = "SELECT et.etudiant_id AS id, et.nom, et.prenom, et.cne, et.date_naissance AS dateNaissance, et.adresse FROM etudiants et WHERE et.cne=?;";
    private final String INSERT_NOTE_SQL = "INSERT INTO `notes` (`note`, `etudiant_id`, `module_id`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `note` = VALUES(`note`);";
    private final String ADD_ABSENCE_SQL = "INSERT INTO absence (etudiant_id, module_id, absence_date) VALUES (?, ?, ?)";
    private final String REDUCE_NOTE_SQL = "UPDATE notes SET note = GREATEST(note - 1, 0) WHERE etudiant_id = ? AND module_id = ?";

    // DECLARATIONS
    private List<StudentBasicModel> studentList = new ArrayList<>();
    private DaoFactory daoFactory;

    public StudentBasicService( DaoFactory daoFactory) {

        // INSTANCING DAO FACTORY
        this.daoFactory = daoFactory;
    }

    @Override
    public List<StudentBasicModel> fetchAll() throws DaoGeneratedException {

        // TESTING DB CONNECTION
        try(Connection conn = daoFactory.getConnection()) {
            if(conn != null){

                // DB CONNECTED - WILL AUTOMATICALLY CLOSE

                // PREPARING THE QUERY
                try(PreparedStatement preparedStatement = conn.prepareStatement(SELECT_STUDENTS_SQL)){

                    // NOTHING TO BIND ->> EXECUTE
                    try(ResultSet resultSet = preparedStatement.executeQuery()){

                        while(resultSet.next()){

                            // LOOPING THE RESULT

                            // ADDING NEW STUDENT
                            StudentBasicModel student = new StudentBasicModel();

                            // SETTING STUDENT ATTRIBUTES
                            student.setId(resultSet.getInt("id"));
                            student.setNom(resultSet.getString("nom"));
                            student.setPrenom(resultSet.getString("prenom"));
                            student.setCne(resultSet.getString("cne"));
                            student.setAdresse(resultSet.getString("adresse"));
                            student.setDateNaissance(resultSet.getDate("dateNaissance"));

                            // APPENDING STUDENTS LIST
                            studentList.add(student);
                        }
                    }
                }
            }

        }catch(SQLException e){

            // SOMETHING WENT WRONG OR DB NOT CONNECTED
            throw new DaoGeneratedException("SOMETHING WENT WRONG -- STUDENTS NOT FETCHED");
        }

        // STUDENT LIST [SET, NULL]
        return studentList;
    }

    @Override
    public StudentBasicModel search(String cne) throws DaoGeneratedException{

        // TESTING DB CONNECTION
        try(Connection conn = daoFactory.getConnection()) {
            if(conn != null){

                // DB CONNECTED - WILL AUTOMATICALLY CLOSE

                // PREPARING THE QUERY
                try(PreparedStatement preparedStatement = conn.prepareStatement(SELECT_STUDENT_SQL)){

                    // BINDING PARAMS
                    preparedStatement.setString(1, cne);

                    // EXECUTION
                    try(ResultSet resultSet = preparedStatement.executeQuery()){

                        if(resultSet.next()){

                            // STUDENT FOUND

                            // ADDING NEW STUDENT
                            StudentBasicModel student = new StudentBasicModel();

                            // SETTING STUDENT ATTRIBUTES
                            student.setId(resultSet.getInt("id"));
                            student.setNom(resultSet.getString("nom"));
                            student.setPrenom(resultSet.getString("prenom"));
                            student.setCne(resultSet.getString("cne"));
                            student.setAdresse(resultSet.getString("adresse"));
                            student.setDateNaissance(resultSet.getDate("dateNaissance"));

                            // RETURNING STUDENT
                            return student;
                        }
                    }
                }
            }

        }catch(SQLException e){

            // SOMETHING WENT WRONG OR DB NOT CONNECTED
            throw new DaoGeneratedException("SOMETHING WENT WRONG -- STUDENT NOT FOUND");
        }

        // SOMETHING WENT WRONG
        return null;
    }

    @Override
    public boolean insert(StudentBasicModel student) throws DaoGeneratedException {

        // TESTING DB CONNECTION
        try(Connection conn = daoFactory.getConnection()) {
            if(conn != null){

                // DB CONNECTED - WILL AUTOMATICALLY CLOSE

                // PREPARING THE QUERY
                try(PreparedStatement preparedStatement = conn.prepareStatement(INSERT_STUDENT_SQL)){

                    // BINDING PARAMS
                    preparedStatement.setString(1, student.getNom());
                    preparedStatement.setString(2, student.getPrenom());
                    preparedStatement.setDate(3, student.getDateNaissance());
                    preparedStatement.setString(4, student.getCne());
                    preparedStatement.setString(5, student.getAdresse());

                    // EXECUTION
                    int rowsAffected = preparedStatement.executeUpdate();

                    // RETURN STATUS
                    return rowsAffected > 0;
                }
            }

        }catch(SQLException e){

            // SOMETHING WENT WRONG OR DB NOT CONNECTED
            throw new DaoGeneratedException("SOMETHING WENT WRONG -- STUDENT NOT INSERTED");
        }

        // STUDENT NOT INSERTED
        return false;
    }

    @Override
    public boolean update(StudentBasicModel student) throws DaoGeneratedException {

        // TESTING DB CONNECTION
        try (Connection conn = daoFactory.getConnection()) {
            if (conn != null) {

                // DB CONNECTED - WILL AUTOMATICALLY CLOSE
                // PREPARING THE QUERY
                try (PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_STUDENT_SQL)) {

                    // BINDING PARAMS
                    preparedStatement.setString(1, student.getNom());
                    preparedStatement.setString(2, student.getPrenom());
                    preparedStatement.setDate(3, student.getDateNaissance());
                    preparedStatement.setString(4, student.getAdresse());
                    preparedStatement.setString(5, student.getCne());

                    // EXECUTION
                    int rowsAffected = preparedStatement.executeUpdate();

                    // RETURN STATUS
                    return rowsAffected > 0;
                }
            }
        } catch (SQLException e) {

            // SOMETHING WENT WRONG OR DB NOT CONNECTED
            throw new DaoGeneratedException("SOMETHING WENT WRONG -- STUDENT NOT UPDATED");
        }

        // STUDENT NOT UPDATED
        return false;
    }

    @Override
    public boolean delete(String cne) throws DaoGeneratedException {

        // TESTING DB CONNECTION
        try (Connection conn = daoFactory.getConnection()) {
            if (conn != null) {

                // DB CONNECTED - WILL AUTOMATICALLY CLOSE
                // PREPARING THE QUERY
                try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_STUDENT_SQL)) {

                    // BINDING PARAMS
                    preparedStatement.setString(1, cne);

                    // EXECUTION
                    int rowsAffected = preparedStatement.executeUpdate();

                    // RETURN STATUS
                    return rowsAffected > 0;
                }
            }
        } catch (SQLException e) {

            // SOMETHING WENT WRONG OR DB NOT CONNECTED
            throw new DaoGeneratedException("SOMETHING WENT WRONG -- STUDENT NOT DELETED");
        }

        // STUDENT NOT DELETED
        return false;
    }

    @Override
    public boolean insertNote(int studentId, int moduleId, int note) throws DaoGeneratedException {

        // TESTING DB CONNECTION
        try(Connection conn = daoFactory.getConnection()) {
            if(conn != null){

                // DB CONNECTED - WILL AUTOMATICALLY CLOSE

                // PREPARING THE QUERY
                try(PreparedStatement preparedStatement = conn.prepareStatement(INSERT_NOTE_SQL)){

                    // BINDING PARAMS
                    preparedStatement.setInt(1, note);
                    preparedStatement.setInt(2, studentId);
                    preparedStatement.setInt(3, moduleId);

                    // EXECUTION
                    int rowsAffected = preparedStatement.executeUpdate();

                    // RETURN STATUS
                    System.out.println("affected rows: " + rowsAffected);
                    return rowsAffected > 0;
                }
            }

        }catch(SQLException e){
            // SOMETHING WENT WRONG OR DB NOT CONNECTED
            throw new DaoGeneratedException("SOMETHING WENT WRONG -- STUDENT'S NOTE ["+ moduleId +"] NOT INSERTED");
        }

        // NOTE NOT INSERTED / UPDATED
        return false;
    }

    @Override
    public boolean insertAbsence(int studentId, int moduleId, String absenceDate) throws DaoGeneratedException {

        // TESTING DB CONNECTION
        try (Connection conn = daoFactory.getConnection()) {
            if (conn != null) {

                // DB CONNECTED - WILL AUTOMATICALLY CLOSE

                try {
                    // STARTING TRANSACTION
                    conn.setAutoCommit(false);

                    int affectedRows = 0;

                    // 1. ADD ABSENCE
                    try (PreparedStatement addAbsenceStatement = conn.prepareStatement(ADD_ABSENCE_SQL)) {

                        // BINDING PARAMS
                        addAbsenceStatement.setInt(1, studentId);
                        addAbsenceStatement.setInt(2, moduleId);
                        addAbsenceStatement.setString(3, absenceDate);

                        // EXECUTION
                        affectedRows = addAbsenceStatement.executeUpdate();
                    }

                    // 2. NOTE REDUCTION ( IF NOTE EQ 0 --> DO NOTHING )
                    try (PreparedStatement reduceNoteStatement = conn.prepareStatement(REDUCE_NOTE_SQL)) {

                        //BINDING PARAMS
                        reduceNoteStatement.setInt(1, studentId);
                        reduceNoteStatement.setInt(2, moduleId);

                        // EXECUTION
                        reduceNoteStatement.executeUpdate();
                    }

                    // NO EXCEPTION OCCURRED -- COMMIT TRANSACTION
                    conn.commit();

                    // DISABLE TRANSACTION
                    conn.setAutoCommit(true);

                    // RETURN STATUS
                    return affectedRows > 0;

                } catch (SQLException e) {

                    // EXCEPTION OCCURRED CANCEL (ROLLBACK)
                    conn.rollback();

                    // DISABLE TRANSACTION
                    conn.setAutoCommit(true);

                    throw new DaoGeneratedException("SOMETHING WENT WRONG -- ABSENCE NOT INSERTED");
                }
            }
        } catch (SQLException e) {

            // SOMETHING WENT WRONG OR DB NOT CONNECTED
            throw new DaoGeneratedException("SOMETHING WENT WRONG -- DATABASE MAY BE NOT CONNECTED");
        }

        // ABSENCE NOT UPDATED
        return false;
    }
}
