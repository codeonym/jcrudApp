package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.controllers;

import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.Factory.DaoFactory;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao.*;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DatabaseException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.filters.UserInputFilter;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.helpers.Alert;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.*;

public class AdminController extends HttpServlet {

    // INITIALIZING THE VIEWS VARIABLES:
    private final String LOGIN_PAGE = "/WEB-INF/views/login.jsp";
    private final String ERRORS_PAGE = "/WEB-INF/views/errors.jsp";
    private final String HOME_PAGE = "/WEB-INF/views/home.jsp";

    // INITIALIZING THE GLOBAL OBJS
    private DaoFactory daoFactory;
    private StudentBasicDao studentBasicService;
    private StudentExtraDao studentExtraService;
    private AbsenceDao absenceDaoService;
    private NoteDao noteDaoService;

    public void init() {

        // INITIALIZING DAO FACTORY
        try {
            daoFactory = DaoFactory.getInstance();
            studentBasicService = daoFactory.getStudentBasicDao();
            studentExtraService = daoFactory.getStudentExtraDao();
            absenceDaoService = daoFactory.getAbsenceDao();
            noteDaoService = daoFactory.getNoteDao();

        } catch (DatabaseException e) {

            // PRINTING THE EXCEPTION MESSAGE
            System.err.println(e.getMessage());
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // CHECK AUTHENTICATION STATUS
        if(!this.checkAuthenticationStatus(request, response)) {

            // NOT AUTHENTICATED
            handlingForward(request, response, LOGIN_PAGE);

            // END EXECUTION
            return;
        }

        // SESSION UPDATE
        SessionController.updateSession(request, response, daoFactory);

        // FETCHING THE ACTION FROM REQUEST
        String getAction = request.getParameter("action");

        // FORWARDING ACTION
        if( getAction != null && !getAction.isEmpty()){
            switch(getAction) {
                case "search_student":

                    // CALLING SEARCH STUDENT ACTION
                    this.searchStudentAction(request, response);
                    break;
            }
        }


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // CHECK AUTHENTICATION STATUS
        if(!this.checkAuthenticationStatus(request, response)) {

            // NOT AUTHENTICATED
            handlingForward(request, response, LOGIN_PAGE);

            // END EXECUTION
            return;
        }


        // GETTING THE ACTION FROM REQUEST
        String postAction = request.getParameter("action");

        // MESSAGE STATUS
        Alert alert = null;

        if(postAction != null && !postAction.isEmpty()){
            // FORWARDING ACTION
            switch(postAction) {
                case "edit_student":

                    // CALLING EDIT STUDENT ACTION
                    alert = this.editStudentAction(request, response)? new Alert(1, "Student Updated Successfully") : new Alert(0, "Failed To Update Student");
                    break;
                case "delete_student":

                    // CALLING EDIT STUDENT ACTION
                    alert = this.deleteStudentAction(request, response)? new Alert(1, "Student Deleted Successfully") : new Alert(0, "Failed To Delete Student");
                    break;
                case "add_student":

                    // CALLING EDIT STUDENT ACTION
                    alert = this.insertStudentAction(request, response)?new Alert(1,  "Student Added Successfully" ): new Alert(0, "Failed To Add Student");
                    break;
                case "absence_student":

                    // CALLING INSERT ABSENCE STUDENT ACTION
                    alert = this.insertAbsenceStudentAction(request, response)? new Alert(1, "Student's Absence Record Updated Successfully") : new Alert(0, "Failed To Update Student's Absence Record");
                    break;
                case "notes_student":

                    // CALLING INSERT NOTES STUDENT ACTION
                    alert = this.insertNotesStudentAction(request, response)? new Alert(1, "Student's Notes Updated Successfully") : new Alert(0, "Failed To Update Student's Notes");
                    break;
            }
            // SETTING THE ALERT
            request.setAttribute("alert", alert);

            // SESSION UPDATE
            SessionController.updateSession(request, response, daoFactory);
        }

        // FORWARD REQUEST
        handlingForward(request, response, HOME_PAGE);

    }

    private boolean deleteStudentAction(HttpServletRequest request, HttpServletResponse response) {

        // GET STUDENT ID AND APPLY FILTERS
        String cne = UserInputFilter.testInput(request.getParameter("delete_cne"));

        if(cne != null) {

            // STUDENT ID IS SET
            // EXECUTING THE QUERY
            try {
                return studentBasicService.delete(cne);
            } catch (DaoGeneratedException e) {

                // PRINT THE EXCEPTION MESSAGE TO STDERR
                System.err.println(e.getMessage());
            }

        }

        // FAILED TO DELETE
        return false;
    }

    private boolean insertNotesStudentAction(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);

        // GETTING MODULES FROM SESSION
        List<ModuleModel> modules = (List<ModuleModel>)session.getAttribute("modules");

        // GETTING STUDENTS FROM SESSION
        List<StudentBasicModel> students = (List<StudentBasicModel>)session.getAttribute("students");

        // INIT ID
        Integer id = null;

        int status = 0;

        // GET STUDENT ID
        String cne = UserInputFilter.testInput(request.getParameter("noteCne"));
        for (StudentBasicModel student : students){

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
                note = Integer.parseInt(UserInputFilter.testInput(request.getParameter("module_" + module.getModuleId())));
                if( note >= 0 && note <= 20) {

                    // THE NOTE IS SET PROPERLY
                    try {
                        // EXECUTE
                        if (noteDaoService.insertNote(id, moduleId, note)) {
                            status ++;
                        }
                    } catch (DaoGeneratedException e) {

                        System.err.println(e.getMessage());
                    }
                }

            }
        }

        // RETURN IF ANY ROWS AFFECTED
        return status > 0;
    }

    private boolean insertAbsenceStudentAction(HttpServletRequest request, HttpServletResponse response) {

        // GETTING DATA FROM USER
        String cne = UserInputFilter.testInput(request.getParameter("absenceCne"));
        Integer moduleId = Integer.parseInt(UserInputFilter.testInput(request.getParameter("absenceModule")));
        String dateString = UserInputFilter.testInput(request.getParameter("absenceDate"));

        // FORMATTING & PARSING THE DATETIME TO MATCH MYSQL RECORD
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // PARSING
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, inputFormatter);

        // VALIDATION
        if( !cne.isEmpty() && moduleId != null && UserInputFilter.isValidYear(localDateTime)) {

            // DEFINING FORMATTER TO MATCH MYSQL FORMAT
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // FORMATTING
            String absenceDate = localDateTime.format(outputFormatter);
            // DECLARING STUDENT ID
            Integer id = null;

            // GETTING THE ID

            HttpSession session = request.getSession(false);

            if(session != null) {

                // SESSION IS SET AND OPEN
                // GETTING THE STUDENTS LIST FROM SESSION
                List <StudentBasicModel> students = (List<StudentBasicModel>) session.getAttribute("students");

                for (StudentBasicModel student : students) {
                    if(student.getCne().equals(cne)){

                        // RECORD MATCH
                        // GETTING THE ID
                        id = student.getId();
                    }

                }

                // CHECKING ARGUMENTS VALIDATION
                if(id != null){

                    // PROCEEDING
                    try {
                        // EXECUTE
                        return absenceDaoService.insertAbsence(id, moduleId, absenceDate);
                    } catch (DaoGeneratedException e) {

                        System.err.println(e.getMessage());
                    }
                }
            }
        }

        // FAILED
        return false;
    }

    private boolean insertStudentAction(HttpServletRequest request, HttpServletResponse response) {

        // GETTING DATA FROM USER
        String nom = UserInputFilter.testInput(request.getParameter("addNom"));
        String prenom = UserInputFilter.testInput(request.getParameter("addPrenom"));
        String cne = UserInputFilter.testInput(request.getParameter("addCne"));
        String adresse = UserInputFilter.testInput(request.getParameter("addAdresse"));
        String dateNaissanceString = UserInputFilter.testInput(request.getParameter("addDateNaissance"));

        if (nom != null && prenom != null && cne != null && adresse != null && dateNaissanceString != null) {

            // EVERYTHING IS SET

            // PARSING DATE TO SQL DATE
            LocalDate localDateNaissance = null;
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            localDateNaissance = LocalDate.parse(dateNaissanceString, dateFormatter);

            // VALIDATING INPUTS
            if (UserInputFilter.isValidName(nom)
                    && UserInputFilter.isValidName(prenom)
                    && UserInputFilter.isValidIdentifier(cne)
                    && UserInputFilter.isValidAddress(adresse)
                    && UserInputFilter.isValidBirthDate(localDateNaissance)) {
                // ALL FIELDS ARE VALID
                // PROCEEDING ...

                try {
                    Date dateNaissance = Date.valueOf(localDateNaissance);

                    // CONTAINER FOR STUDENT DATA
                    StudentBasicModel student = new StudentBasicModel();

                    // FILLING THE CONTAINER WITH ACTUAL DATA
                    student.setNom(nom);
                    student.setDateNaissance(dateNaissance);
                    student.setAdresse(adresse);
                    student.setPrenom(prenom);
                    student.setCne(cne);

                    // EXECUTING
                    return studentBasicService.insert(student);
                } catch (DaoGeneratedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        // STUDENT NOT INSERTED
        return false;
    }

    private boolean editStudentAction(HttpServletRequest request, HttpServletResponse response) {

        // GETTING DATA FROM USER
        String nom = UserInputFilter.testInput(request.getParameter("editNom"));
        String prenom = UserInputFilter.testInput(request.getParameter("editPrenom"));
        String cne = UserInputFilter.testInput(request.getParameter("editCne"));
        String adresse = UserInputFilter.testInput(request.getParameter("editAdresse"));
        String dateNaissanceString = UserInputFilter.testInput(request.getParameter("editDateNaissance"));

        if (nom != null && prenom != null && cne != null && adresse != null && dateNaissanceString != null) {

            // EVERYTHING IS SET

            // PARSING DATE TO SQL DATE
            LocalDate localDateNaissance = null;
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            localDateNaissance = LocalDate.parse(dateNaissanceString, dateFormatter);

            // VALIDATING INPUTS
            if (UserInputFilter.isValidName(nom)
                    && UserInputFilter.isValidName(prenom)
                    && UserInputFilter.isValidIdentifier(cne)
                    && UserInputFilter.isValidAddress(adresse)
                    && UserInputFilter.isValidBirthDate(localDateNaissance)) {
                // ALL FIELDS ARE VALID
                // PROCEEDING ...

                try {
                    Date dateNaissance = Date.valueOf(localDateNaissance);

                    // CONTAINER FOR STUDENT DATA
                    StudentBasicModel student = new StudentBasicModel();

                    // FILLING THE CONTAINER WITH ACTUAL DATA
                    student.setNom(nom);
                    student.setDateNaissance(dateNaissance);
                    student.setAdresse(adresse);
                    student.setPrenom(prenom);
                    student.setCne(cne);

                    // EXECUTING
                    return studentBasicService.update(student);
                } catch (DaoGeneratedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        // STUDENT NOT UPDATED
        return false;
    }

    private boolean searchStudentAction(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");

        boolean status = false;

        // GETTING THE PARAMETER
        String cne = UserInputFilter.testInput(request.getParameter("searchCne"));

        if(!cne.isEmpty() && UserInputFilter.isValidIdentifier(cne)) {
            // THE PROVIDED PARAMETER IS VALID

            // SEARCH STUDENT
            StudentBasicModel student = null;
            try {
                student = studentBasicService.search(cne);
            } catch (DaoGeneratedException e) {

                System.err.println(e.getMessage());
            }

            if(student != null) {
                // STUDENT FOUND

                // RENDERING THE RESULT TO REQUEST
                request.setAttribute("searchStudent", student);
                request.setAttribute("searchForm", true);
                status = true;

            }
        }

        // FORWARDING TO HOME PAGE
        handlingForward(request, response, HOME_PAGE);

        return status;
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