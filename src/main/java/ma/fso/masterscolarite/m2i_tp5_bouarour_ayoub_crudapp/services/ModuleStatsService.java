package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.Factory.DaoFactory;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao.ModuleStatsDao;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.ModuleStatModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModuleStatsService  implements ModuleStatsDao {

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
    private DaoFactory daoFactory;

    public ModuleStatsService( DaoFactory daoFactory) {

        // INSTANCING DAO FACTORY
        this.daoFactory = daoFactory;
    }

    @Override
    public ModuleStatModel get(int studentId, String studentCne, int moduleId) throws DaoGeneratedException {

        // TESTING DB CONNECTION
        try(Connection conn = daoFactory.getConnection()) {
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
                            ModuleStatModel module = new ModuleStatModel();

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
            throw new DaoGeneratedException("SOMETHING WENT WRONG -- MODULE'S STATS NOT FETCHED");
        }

        // SOMETHING WENT WRONG
        return null;
    }
}
