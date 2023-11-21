package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.controllers;

import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import ma.fso.auth.m2i_tp3_ex2_bouarour_ayoub.utility.Hashing;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.*;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services.AuthService;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services.ModulesService;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services.StudentMoreService;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.services.StudentService;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.utilities.DatabaseConnect;
import org.hibernate.Session;
import org.hibernate.boot.model.relational.Database;

public class AdminController extends HttpServlet {

    // INITIALIZING THE VIEWS VARIABLES:
    private final String loginPage = "/WEB-INF/views/login.jsp";
    private final String errorPage = "/WEB-INF/views/errors.jsp";
    private final String homePage = "/WEB-INF/views/home.jsp";

    // INITIALIZING THE GLOBAL OBJS
    StudentService studentService = new StudentService();

    public void init() {
        // TESTING DATABASE CONNECTIVITY
        try(Connection conn = DatabaseConnect.getConnection()){
            // THE CONNECTION WILL BE CLOSED AUTOMATICALLY AFTER THE END OF TRY-CATCH

            // CHECK THE CONNECTIVITY
            if(conn != null){
                // CONNECTED SUCCESSFULLY
                System.out.println("\n :: ~ NOTICE: CONNECTION ESTABLISHED TO THE DATABASE SERVER.");
            }
        }catch(Exception e){
            // DEBUGGING
            System.out.println("\n :: ^ ERROR: CONNECTION REFUSED TO THE DATABASE SERVER !");
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // CHECK AUTHENTICATION STATUS
        if(!this.checkAuthenticationStatus(request, response)) {

            // NOT AUTHENTICATED
            handlingForward(request, response, loginPage);
            // END EXECUTION
            return;
        }

        // SESSION UPDATE
        SessionController.updateSession(request, response);

        // FETCHING THE ACTION FROM REQUEST
        String getAction = request.getParameter("action");

        // FORWARDING ACTION
        switch(getAction) {
            case "search_student":

                // CALLING SEARCH STUDENT ACTION
                this.searchStudentAction(request, response);
                break;
        }


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // CHECK AUTHENTICATION STATUS
        if(!this.checkAuthenticationStatus(request, response)) {

            // NOT AUTHENTICATED
            handlingForward(request, response, loginPage);
            // END EXECUTION
            return;
        }


        // FETCHING THE ACTION FROM REQUEST
        String postAction = request.getParameter("action");

        // FORWARDING ACTION
        switch(postAction) {
            case "edit_student":

                // CALLING EDIT STUDENT ACTION
                this.editStudentAction(request, response);
                break;
            case "delete_student":

                // CALLING EDIT STUDENT ACTION
                this.deleteStudentAction(request, response);
                break;
            case "add_student":

                // CALLING EDIT STUDENT ACTION
                this.insertStudentAction(request, response);
                break;
            case "absence_student":

                // CALLING INSERT ABSENCE STUDENT ACTION
                this.insertAbsenceStudentAction(request, response);
                break;
            case "notes_student":

                // CALLING INSERT NOTES STUDENT ACTION
                this.insertNotesStudentAction(request, response);
                break;
        }

        // SESSION UPDATE
        SessionController.updateSession(request, response);

        // FORWARD REQUEST
        handlingForward(request, response, homePage);

    }

    private void deleteStudentAction(HttpServletRequest request, HttpServletResponse response) {

        // GET STUDENT ID
        String cne = request.getParameter("delete_cne");

        if(cne != null) {

            // STUDENT ID IS SET
            // EXECUTING THE QUERY
            studentService.deleteStudent(cne);

        }
    }

    private void insertNotesStudentAction(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        // GETTING MODULES FROM SESSION
        List<ModuleModel> modules = (List<ModuleModel>)session.getAttribute("modules");

        // GETTING STUDENTS FROM SESSION
        List<StudentModel> students = (List<StudentModel>)session.getAttribute("students");

        // INIT ID
        Integer id = null;

        // GET STUDENT ID
        String cne = request.getParameter("noteCne");
        for (StudentModel student : students){

            if (cne.equals(student.getCne())){
                // STUDENT FOUND
                // FETCHING THE ID
                id = student.getId();
            }
        }

        if(id != null) {

            // VALID STUDENT ID
            // GETTING NOTES FROM USER
            for(ModuleModel module: modules) {

                // TEMP VARS
                Integer note = null;
                int moduleId = module.getModuleId();

                // PARSING NOTE FROM PARAMETER -- EXPECTED FORMAT name="module_${moduleId} << APPENDING NOTES LIST
                note = Integer.parseInt(request.getParameter("module_" + module.getModuleId() ));

                if(note != null) {

                    // THE NOTE IS SET PROPERLY
                    // INSERTING THE NOTE
                    studentService.insertNote(id, moduleId, note);
                }

            }
        }


    }

    private void insertAbsenceStudentAction(HttpServletRequest request, HttpServletResponse response) {

        // GETTING DATA FROM USER
        String cne = request.getParameter("absenceCne");
        int moduleId = Integer.parseInt(request.getParameter("absenceModule"));
        String dateString = request.getParameter("absenceDate");

        // FORMATTING & PARSING THE DATETIME TO MATCH MYSQL RECORD
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // PARSING
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, inputFormatter);

        // DEFINING FORMATTER TO MATCH MYSQL FORMAT
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // FORMATTING
        String absenceDate = localDateTime.format(outputFormatter);

        System.out.println("\n :: ~ cne:" + cne + " id:" + moduleId + " date: " + dateString + " formatted: " + absenceDate);

        // DECLARING STUDENT ID
        Integer id = null;

        // GETTING THE ID

        HttpSession session = request.getSession(false);

        if(session != null) {

            // SESSION IS SET AND OPEN
            // GETTING THE STUDENTS LIST FROM SESSION
            List <StudentModel> students = (List<StudentModel>) session.getAttribute("students");

            for (StudentModel student : students) {
                if(student.getCne().equals(cne)){

                    // RECORD MATCH
                    // GETTING THE ID
                    id = student.getId();
                }

            }

            // CHECKING ARGUMENTS VALIDATION
            if(id != null && !absenceDate.isEmpty() && moduleId != 0){

                // PROCEEDING
                studentService.addAbsence(id, moduleId, absenceDate);
            }
        }

        //
    }

    private void insertStudentAction(HttpServletRequest request, HttpServletResponse response) {

        // GETTING DATA FROM USER
        String nom = request.getParameter("addNom");
        String prenom = request.getParameter("addPrenom");
        String cne = request.getParameter("addCne");
        String adresse = request.getParameter("addAdresse");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNaissance = null;
        try {

            // PARSING ...
            java.util.Date utilDate = dateFormat.parse(request.getParameter("addDateNaissance"));
            dateNaissance = new Date(utilDate.getTime());

        } catch (ParseException e) {
            // ERROR WHILE PARSING DATE TO SUITABLE SQL FORMAT
            //DEBUGGING
            e.printStackTrace();
        }

        if(nom != null && prenom != null && cne != null && adresse != null && dateNaissance != null){
            // EVERYTHING IS SET

            // CONTAINER FOR STUDENT DATA
            StudentModel student = new StudentModel();

            //FILLING THE CONTAINER WITH ACTUAL DATA
            student.setNom(nom);
            student.setDateNaissance(dateNaissance);
            student.setAdresse(adresse);
            student.setPrenom(prenom);
            student.setCne(cne);

            studentService.insertStudent(student);

        }else {

            // SOMETHING WENT WRONG OR DATA INVALID
            System.out.println("\n:: ^ Student Not Inserted.");
        }
    }

    private void editStudentAction(HttpServletRequest request, HttpServletResponse response) {

        // GETTING DATA FROM USER
        String nom = request.getParameter("editNom");
        String prenom = request.getParameter("editPrenom");
        String cne = request.getParameter("editCne");
        String adresse = request.getParameter("editAdresse");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNaissance = null;
        try {

            // PARSING ...
            java.util.Date utilDate = dateFormat.parse(request.getParameter("editDateNaissance"));
            dateNaissance = new Date(utilDate.getTime());

        } catch (ParseException e) {
            // ERROR WHILE PARSING DATE TO SUITABLE SQL FORMAT
            // DEBUGGING
            e.printStackTrace();
        }

        if (nom != null && prenom != null && cne != null && adresse != null && dateNaissance != null) {
            // EVERYTHING IS SET

            // CONTAINER FOR STUDENT DATA
            StudentModel student = new StudentModel();

            // FILLING THE CONTAINER WITH ACTUAL DATA
            student.setNom(nom);
            student.setDateNaissance(dateNaissance);
            student.setAdresse(adresse);
            student.setPrenom(prenom);
            student.setCne(cne);

            studentService.updateStudent(student);

        } else {

            // SOMETHING WENT WRONG OR DATA INVALID
            System.out.println("\n:: ^ Student Not Updated.");
        }
    }

    private void searchStudentAction(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");

        // GETTING THE PARAMETER
        String cne = request.getParameter("searchCne");

        if(!cne.isEmpty()) {
            // THE PROVIDED PARAMETER IS VALID

            // SEARCH STUDENT
            StudentModel student = studentService.searchStudent(cne);

            if(student != null) {
                // STUDENT FOUND

                // RENDERING THE RESULT TO REQUEST
                request.setAttribute("searchStudent", student);

            }
        }

        // FORWARDING TO HOME PAGE
        handlingForward(request, response, homePage);

    }

    private void handlingForward(HttpServletRequest request, HttpServletResponse response, String target){

        // DISPATCHING TO SPECIFIC TARGET
        RequestDispatcher rd = request.getRequestDispatcher(target);
        try {

            // FORWARDING ..
            rd.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkAuthenticationStatus(HttpServletRequest request, HttpServletResponse response){

        //INSTANCING THE SESSION OBJECT
        HttpSession session = request.getSession();


        // GETTING THE USER FROM THE SESSION
        UserModel user = (UserModel) session.getAttribute("user");

        // RETURN CONNECTION STATUS
        return user != null;
    }

    public void destroy() {
    }
}