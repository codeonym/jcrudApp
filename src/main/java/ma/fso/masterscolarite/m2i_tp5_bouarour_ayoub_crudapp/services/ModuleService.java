package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.Factory.DaoFactory;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao.ModuleDao;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.ModuleModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModuleService implements ModuleDao {

    // QUERIES
    private final String SELECT_MODULES_SQL = "SELECT module_id, module_nom FROM modules; ";

    // DECLARATIONS

    private ArrayList<ModuleModel> modules = new ArrayList<ModuleModel>();
    private DaoFactory daoFactory;

    public ModuleService( DaoFactory daoFactory) {

        // INSTANCING DAO FACTORY
        this.daoFactory = daoFactory;
    }
    @Override
    public List<ModuleModel> fetchAll() throws DaoGeneratedException {

        // TESTING DB CONNECTION
        try(Connection conn = daoFactory.getConnection()) {
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
            throw new DaoGeneratedException("SOMETHING WENT WRONG -- MODULES NOT FETCHED");
        }

        // MODULES LIST [SET, NULL]
        return modules;
    }
}
