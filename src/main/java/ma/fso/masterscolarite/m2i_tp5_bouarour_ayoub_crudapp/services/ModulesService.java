package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.ModuleModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.ModulesModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.StudentModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.utilities.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModulesService {

    // QUERIES
    private final String SELECT_MODULES_SQL = "SELECT module_id, module_nom FROM modules; ";
    private final String SELECT_MODULE_SQL ="SELECT m.module_nom AS moduleNom, " +
            "e.etudiant_id AS moduleEtudiant_id," +
            " COALESCE(n.note, 0) AS moduleNote," +
            " COALESCE(COUNT(a.absence_id), 0) AS moduleAbsence " +
            "FROM modules m " +
            "JOIN etudiants e ON e.etudiant_id =? " +
            "LEFT JOIN notes n ON m.module_id = n.module_id AND e.etudiant_id = n.etudiant_id " +
            "LEFT JOIN absence a ON m.module_id = a.module_id AND e.etudiant_id = a.etudiant_id " +
            "WHERE m.module_id =? " +
            "GROUP BY m.module_id, m.module_nom, e.etudiant_id, n.note;";

    // DECLARATIONS

    private ArrayList<ModuleModel> modules = new ArrayList<ModuleModel>();

    public List<ModuleModel> fetchAllModules() throws SQLException {

        // TESTING DB CONNECTION
        try(Connection conn = DatabaseConnect.getConnection()) {
            if(conn != null){

                // DB CONNECTED - WILL AUTOMATICALLY CLOSE

                // PREPARING THE QUERY
                try(PreparedStatement preparedStatement = conn.prepareStatement(SELECT_MODULES_SQL)){

                    // NOTHING TO BIND ->> EXECUTE
                    try(ResultSet resultSet = preparedStatement.executeQuery()){

                        while(resultSet.next()){

                            // LOOPING THE RESULT

                            // ADDING NEW MODULE
                            ModuleModel module = new ModuleModel();

                            // SETTING MODULE ATTRIBUTES
                            module.setModuleId(resultSet.getInt("module_id"));
                            module.setModuleNom(resultSet.getString("module_nom"));

                            // APPENDING MODULE TO MODULES LIST
                            modules.add(module);
                        }
                    }
                }
            }

        }catch(SQLException e){
            // SOMETHING WENT WRONG OR DB NOT CONNECTED
            // DEBUGGING
            e.printStackTrace();
        }

        // MODULES LIST [SET, NULL]
        return modules;
    }

    public ModulesModel getModuleData(int studentId, String studentCne, int moduleId) throws SQLException {

        // TESTING DB CONNECTION
        try(Connection conn = DatabaseConnect.getConnection()) {
            if(conn != null){

                // DB CONNECTED - WILL AUTOMATICALLY CLOSE

                // PREPARING THE QUERY
                try(PreparedStatement preparedStatement = conn.prepareStatement(SELECT_MODULE_SQL)){

                    // BINDING PARAMS
                    preparedStatement.setInt(1, studentId);
                    preparedStatement.setInt(2, moduleId);

                    // EXECUTION
                    try(ResultSet resultSet = preparedStatement.executeQuery()){

                        if(resultSet.next()){

                            // RECORD FOUND

                            // ADDING NEW MODULE
                            ModulesModel module = new ModulesModel();

                            // SETTING MODULE ATTRIBUTES
                            module.setModuleAbsence(resultSet.getInt("moduleAbsence"));
                            module.setModuleNom(resultSet.getString("moduleNom"));
                            module.setModuleNote(resultSet.getInt("moduleNote"));
                            module.setStudentCne(studentCne);

                            // RETURNING DATA
                            return module;
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

}
