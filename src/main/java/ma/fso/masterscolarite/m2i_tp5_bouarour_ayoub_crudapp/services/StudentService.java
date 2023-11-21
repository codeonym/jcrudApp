package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.ModuleModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.StudentModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.UserModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.utilities.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    // SQL QUERIES
    private final String INSERT_STUDENT_SQL = "INSERT INTO etudiants (nom, prenom, date_naissance, cne, adresse) VALUES (?, ?, ?, ?, ?);";
    private final String UPDATE_STUDENT_SQL = "UPDATE etudiants SET nom=?, prenom=?, date_naissance=?, adresse=? WHERE cne=?;";
    private final String DELETE_STUDENT_SQL = "DELETE FROM etudiants WHERE cne=?;";
    private final String SELECT_STUDENTS_SQL = "SELECT et.etudiant_id AS id, et.nom, et.prenom, et.cne, et.date_naissance AS dateNaissance, et.adresse FROM etudiants et;";
    private final String SELECT_STUDENT_SQL = "SELECT et.etudiant_id AS id, et.nom, et.prenom, et.cne, et.date_naissance AS dateNaissance, et.adresse FROM etudiants et WHERE et.cne=?;";
    private final String INSERT_NOTE_SQL = "INSERT INTO `notes` (`note`, `etudiant_id`, `module_id`) VALUES (?, ?, ?)";
    private static final String ADD_ABSENCE_SQL = "INSERT INTO absence (etudiant_id, module_id, absence_date) VALUES (?, ?, ?)";
    private static final String REDUCE_NOTE_SQL = "UPDATE notes SET note = GREATEST(note - 1, 0) WHERE etudiant_id = ? AND module_id = ?";

    // DECLARATIONS
    private List<StudentModel> studentList = new ArrayList<>();
    public List<StudentModel> fetchAllStudents() throws SQLException {

        // TESTING DB CONNECTION
        try(Connection conn = DatabaseConnect.getConnection()) {
            if(conn != null){

                // DB CONNECTED - WILL AUTOMATICALLY CLOSE

                // PREPARING THE QUERY
                try(PreparedStatement preparedStatement = conn.prepareStatement(SELECT_STUDENTS_SQL)){

                    // NOTHING TO BIND ->> EXECUTE
                    try(ResultSet resultSet = preparedStatement.executeQuery()){

                        while(resultSet.next()){

                            // LOOPING THE RESULT

                            // ADDING NEW STUDENT
                            StudentModel student = new StudentModel();

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
            // DEBUGGING
            e.printStackTrace();
        }

        // STUDENT LIST [SET, NULL]
        return studentList;
    }

    public StudentModel searchStudent(String cne){

        // TESTING DB CONNECTION
        try(Connection conn = DatabaseConnect.getConnection()) {
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
                            StudentModel student = new StudentModel();

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
            // DEBUGGING
            e.printStackTrace();
        }

        // SOMETHING WENT WRONG
        return null;
    }

    public void insertStudent(StudentModel student) {

        // TESTING DB CONNECTION
        try(Connection conn = DatabaseConnect.getConnection()) {
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

                    // CHECK STATUS
                    if (rowsAffected > 0) {

                        // SUCCEED
                        System.out.println("\n:: ~ Student inserted successfully.");
                    } else {
                        // FAILED
                        System.out.println("\n:: ^ Failed to insert student.");
                    }
                }
            }

        }catch(SQLException e){
            // SOMETHING WENT WRONG OR DB NOT CONNECTED
            // DEBUGGING
            e.printStackTrace();
        }

    }

    public void updateStudent(StudentModel student) {
        // TESTING DB CONNECTION
        try (Connection conn = DatabaseConnect.getConnection()) {
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

                    // CHECK STATUS
                    if (rowsAffected > 0) {
                        // SUCCEED
                        System.out.println("\n:: ~ Student updated successfully.");
                    } else {
                        // FAILED
                        System.out.println("\n:: ^ Failed to update student. Student not found or no changes made.");
                    }
                }
            }
        } catch (SQLException e) {
            // SOMETHING WENT WRONG OR DB NOT CONNECTED
            // DEBUGGING
            e.printStackTrace();
        }
    }

    public void insertNote(int studentId, int moduleId, int note) {

        // TESTING DB CONNECTION
        try(Connection conn = DatabaseConnect.getConnection()) {
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

                    // CHECK STATUS
                    if (rowsAffected > 0) {

                        // SUCCEED
                        System.out.println("\n:: ~ Notes inserted successfully.");
                    } else {
                        // FAILED
                        System.out.println("\n:: ^ Failed to insert notes.");
                    }
                }
            }

        }catch(SQLException e){
            // SOMETHING WENT WRONG OR DB NOT CONNECTED
            // DEBUGGING
            e.printStackTrace();
        }

    }

    public void deleteStudent(String cne) {
        // TESTING DB CONNECTION
        try (Connection conn = DatabaseConnect.getConnection()) {
            if (conn != null) {
                // DB CONNECTED - WILL AUTOMATICALLY CLOSE
                // PREPARING THE QUERY
                try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_STUDENT_SQL)) {

                    // BINDING PARAMS
                    preparedStatement.setString(1, cne);

                    // EXECUTION
                    int rowsAffected = preparedStatement.executeUpdate();

                    // CHECK STATUS
                    if (rowsAffected > 0) {
                        // SUCCEED
                        System.out.println("\n:: ~ Student deleted successfully.");
                    } else {
                        // FAILED
                        System.out.println("\n:: ^ Failed to delete student or Student not found .");
                    }
                }
            }
        } catch (SQLException e) {
            // SOMETHING WENT WRONG OR DB NOT CONNECTED
            // DEBUGGING
            e.printStackTrace();
        }
    }

    public void addAbsence(int studentId, int moduleId, String absenceDate) {

        // TESTING DB CONNECTION
        try (Connection conn = DatabaseConnect.getConnection()) {
            if (conn != null) {
                // DB CONNECTED - WILL AUTOMATICALLY CLOSE

                // STARTING TRANSACTION
                conn.setAutoCommit(false);

                try {
                    // 1. ADD ABSENCE
                    try (PreparedStatement addAbsenceStatement = conn.prepareStatement(ADD_ABSENCE_SQL)) {

                        // BINDING PARAMS
                        addAbsenceStatement.setInt(1, studentId);
                        addAbsenceStatement.setInt(2, moduleId);
                        addAbsenceStatement.setString(3, absenceDate);

                        // EXECUTION
                        addAbsenceStatement.executeUpdate();
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
                } catch (SQLException e) {

                    // EXCEPTION OCCURRED CANCEL (ROLLBACK)
                    conn.rollback();
                    e.printStackTrace();

                    // DEBUGGING
                } finally {

                    // EITHER WAY DISABLE TRANSACTION
                    conn.setAutoCommit(true);
                }
            }
        } catch (SQLException e) {

            // SOMETHING WENT WRONG OR DB NOT CONNECTED
            // DEBUGGING
            e.printStackTrace();
        }
    }
}
