package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.Factory.DaoFactory;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao.ModuleStatsDao;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao.StudentExtraDao;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.ModuleModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.ModuleStatModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.StudentBasicModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.StudentExtraModel;

import java.util.ArrayList;
import java.util.List;

public class StudentExtraService implements StudentExtraDao {

    // DECLARATIONS
    List<StudentExtraModel> studentsExtra = new ArrayList<>();
    private DaoFactory daoFactory;

    public StudentExtraService( DaoFactory daoFactory) {

        // INSTANCING DAO FACTORY
        this.daoFactory = daoFactory;
    }
    public List<StudentExtraModel> getMore(List<StudentBasicModel> students, List<ModuleModel> modules) throws DaoGeneratedException {

        // INSTANCING THE MODULE STATS SERVICE
        ModuleStatsDao moduleStatsService = daoFactory.getModuleStatsDao();

        // GETTING EXTRA DATA FOR EACH STUDENT RECORD
        for (StudentBasicModel student : students) {

            // FOR EACH STUDENT DO =>
            // CREATE A CONTAINER TO HOLD THE RECORDS OF EXTRA DATA
            List <ModuleStatModel> more = new ArrayList<ModuleStatModel>();

            for (ModuleModel module : modules) {

                // FOR EACH MODULE DO =>
                // ADD RECORD TO THE CONTAINER
                try {

                    // GETTING RECORD FOR ITH-STUDENT FOR ITH-MODULE
                    more .add(moduleStatsService.get(student.getId(), student.getCne(), module.getModuleId()));
                } catch (DaoGeneratedException e) {

                    // SOMETHING WENT WRONG
                    throw new DaoGeneratedException("SOMETHING WENT WRONG -- EXTRA DATA NOT FETCHED FOR STUDENT["+ student.getId() +"]!");
                }
            }

            // ADDING THE RECORD CONTAINER TO THE STUDENT-MORE LIST
            studentsExtra.add(new StudentExtraModel(student.getCne(), more));

        }

        // RETURN [SET, NULL]
        return studentsExtra;
    }
}
