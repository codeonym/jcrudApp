package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.Factory.DaoFactory;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao.ModuleDao;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao.StudentBasicDao;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao.StudentExtraDao;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DatabaseException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.ModuleModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.StudentBasicModel;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.StudentExtraModel;

import java.io.IOException;
import java.util.List;

public class SessionController {

    // ERROR PAGE
    private static String ERRORS_PAGE = "/WEB-INF/views/errors.jsp";
    public static void updateSession(HttpServletRequest request, HttpServletResponse response, DaoFactory daoFactory) {

        // UPDATE SESSION AFTER EACH MODIFICATION

        HttpSession session = request.getSession(false);

        if(session != null) {

            // SESSION IS SET AND OPEN
            // FETCHING DATA
            List<StudentBasicModel> students = null;
            List<ModuleModel> modules = null;
            List<StudentExtraModel> studentExtra = null;

             try {

                 // INSTANCING DAO FACTORY
                 daoFactory = DaoFactory.getInstance();
             }catch(DatabaseException e) {

                 // FORWARDING TO ERROR PAGE
                 handlingForward(request, response, ERRORS_PAGE);
                 return;

             }
            StudentBasicDao studentBasicService  = daoFactory.getStudentBasicDao();
            StudentExtraDao studentExtraService = daoFactory.getStudentExtraDao() ;
            ModuleDao moduleService = daoFactory.getModuleDao();

            try {
                students = studentBasicService.fetchAll();
                modules = moduleService.fetchAll();

            } catch (DaoGeneratedException e) {

                // FORWARDING TO ERROR PAGE
                handlingForward(request, response, ERRORS_PAGE);
                return;
            }

            try {
                studentExtra = studentExtraService.getMore(students, modules);
            } catch (DaoGeneratedException e) {

                // PRINT THE EXCEPTION MESSAGE
                System.out.println(e.getMessage());
            }

            if(students != null){

                // CHECK IF THE DATA FETCHED CORRECTLY
                //THEN ADD THE LIST TO THE SESSION
                session.setAttribute("students", students);

                if(modules != null){

                    // ADDING MODULES TO SESSION
                    session.setAttribute("modules", modules);
                }
                if(studentExtra != null){

                    // ADDING STUDENT MORE (EXTRA DATA)  TO SESSION
                    session.setAttribute("studentsMore", studentExtra);
                }
            }
        }

    }
    private static void handlingForward(HttpServletRequest request, HttpServletResponse response, String target){

        // DISPATCHING TO SPECIFIC TARGET
        RequestDispatcher rd = request.getRequestDispatcher(target);
        try {

            // FORWARDING ..
            rd.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
