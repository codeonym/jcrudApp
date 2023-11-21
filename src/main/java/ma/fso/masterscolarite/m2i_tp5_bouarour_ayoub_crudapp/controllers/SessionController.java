package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.ModuleModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.StudentModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.StudentsMoreModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.UserModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services.ModulesService;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services.StudentMoreService;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services.StudentService;

import java.sql.SQLException;
import java.util.List;

public class SessionController {

    public static void updateSession(HttpServletRequest request, HttpServletResponse response) {

        // UPDATE SESSION AFTER EACH MODIFICATION

        HttpSession session = request.getSession(false);

        if(session != null) {

            // SESSION IS SET AND OPEN
            // FETCHING DATA
            List<StudentModel> students = null;
            List<ModuleModel> modules = null;
            List<StudentsMoreModel> studentsMore = null;

            StudentService studentService = new StudentService();
            ModulesService modulesService = new ModulesService();
            StudentMoreService studentMoreService = new StudentMoreService();

            try {
                students = studentService.fetchAllStudents();
                modules = modulesService.fetchAllModules();
                studentsMore = studentMoreService.studentsMore(students, modules);

            } catch (SQLException e) {
                // SOMETHING WENT WRONG ...
                // DEBUGGING
                throw new RuntimeException(e);
            }

            if(students != null){

                // CHECK IF THE DATA FETCHED CORRECTLY
                //THEN ADD THE LIST TO THE SESSION
                session.setAttribute("students", students);

                if(modules != null){

                    // ADDING MODULES TO SESSION
                    session.setAttribute("modules", modules);
                }
                if(studentsMore != null){

                    // ADDING STUDENT MORE (EXTRA DATA)  TO SESSION
                    session.setAttribute("studentsMore", studentsMore);
                }
            }
        }

    }

}
