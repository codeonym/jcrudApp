package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services;

import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.ModuleModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.ModulesModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.StudentModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.StudentsMoreModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentMoreService {

    // DECLARATIONS
    List<StudentsMoreModel> studentsMore = new ArrayList<>();
    ModulesService modulesService = new ModulesService();

    public List<StudentsMoreModel> studentsMore(List<StudentModel> students, List<ModuleModel> modules) {

        // GETTING EACH MODULE DATA FOR EACH STUDENT RECORD
        for (StudentModel student : students) {

            // FOR EACH STUDENT DO =>
            // CREATE A CONTAINER TO HOLD THE RECORDS OF EACH MODULE
            List <ModulesModel> more = new ArrayList<ModulesModel>();

            for (ModuleModel module : modules) {

                // FOR EACH MODULE DO =>
                // ADD RECORD TO THE CONTAINER
                try {
                    more .add(modulesService.getModuleData(student.getId(), student.getCne(), module.getModuleId()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            // ADDING THE RECORD CONTAINER TO THE STUDENT-MORE LIST
            studentsMore.add(new StudentsMoreModel(student.getCne(), more));

        }

        // RETURN [SET, NULL]
        return studentsMore;
    }
}
