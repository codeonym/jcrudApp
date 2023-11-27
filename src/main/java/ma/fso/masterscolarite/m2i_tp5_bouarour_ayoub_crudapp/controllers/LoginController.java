package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.controllers;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import ma.fso.auth.m2i_tp3_ex2_bouarour_ayoub.utility.Hashing;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.Factory.DaoFactory;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.dao.UserDao;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DaoGeneratedException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.exceptions.DatabaseException;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.filters.UserInputFilter;
import ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models.*;

public class LoginController extends HttpServlet {

    // VIEWS VARIABLES:
    private final String LOGIN_PAGE = "/WEB-INF/views/login.jsp";
    private final String HOME_PAGE = "/WEB-INF/views/home.jsp";
    private final String ERRORS_PAGE = "/WEB-INF/views/errors.jsp";

    // GLOBAL DECLARATIONS
    UserDao userAuthService;
    DaoFactory daoFactory ;

    public void init() {

        // INITIALIZING DAO FACTORY
        try {
            daoFactory = DaoFactory.getInstance();
            userAuthService = daoFactory.getUserDao();

        } catch (DatabaseException e) {

            // PRINTING THE EXCEPTION MESSAGE
            System.err.println(e.getMessage());
        }


    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        //INSTANCING THE SESSION OBJECT
        HttpSession session = request.getSession();


        // GETTING THE USER FROM THE SESSION
        UserModel user = (UserModel) session.getAttribute("user");

        if(user != null){

            // USER IS ALREADY AUTHENTICATED

            // SESSION UPDATE
            SessionController.updateSession(request, response, daoFactory );

            handlingForward(request, response, HOME_PAGE);

        }else {
            // USER NOT AUTHENTICATED
            // FORWARDING TO LOGIN PAGE
            handlingForward(request, response, LOGIN_PAGE);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // THIS VARIABLE STORES THE REQUEST ACTION EITHER {LOGIN, LOGOUT}
        String action = request.getParameter("action");

        // CHECKS IF THE ACTION MATCHES LOGOUT THEN PERFORM THE ACTION
        if ("logout".equals(action)){

            // LOGOUT FROM THE APP
            this.logout(request, response);

            //STOP EXECUTION
            return;
        }

        //INSTANCING THE SESSION OBJECT
        HttpSession session = request.getSession();

        // DECLARATIONS
        String username = "",password = "";

        // GET USERNAME, PASSWORD AND APPLY FILTERS
        username = UserInputFilter.testInput(request.getParameter("username"));
        password = UserInputFilter.testInput(request.getParameter("password"));

        // VALIDATING INPUT FIELDS
        if ( username.isEmpty() || password.isEmpty()){

            // INVALID INPUT DETECTED => DO NOTHING
            // REDIRECTION TO LOGIN PAGE
            handlingForward(request, response, LOGIN_PAGE);
        }
        else {

            // CHECK CREDENTIALS VALIDITY
            UserModel user = null;
            try {

                // HASHING THE PASSWORD
                /*
                * NOTICE:
                * WHY CUSTOM HASHING FUNCTION?
                * - FOR BETTER MAINTAINABILITY, FLEXIBILITY
                * - FOR FUTURE CHANGES
                * - FOR DATABASE INDEPENDENCE
                * */
                Hashing hash = new Hashing();
                String hashedPassword = hash.hash_sha256(password);

                // TESTING AUTHENTICATION
                user = userAuthService.authentication(username, hashedPassword);
            } catch (DaoGeneratedException e) {

                System.err.println(e.getMessage());
            }
            if(user != null){

                // USER IS AUTHENTICATED
                // CREATING SESSION FOR USER
                session.setAttribute("user", user);

                // THE SESSION WILL TERMINATE AFTER 5MIN OF INACTIVITY
                session.setMaxInactiveInterval(60*5);

                // SESSION UPDATE
                SessionController.updateSession(request, response, daoFactory);

                // REDIRECTING  TO HOME PAGE
                handlingForward(request, response, HOME_PAGE);
            }else {

                // NOT AUTHENTICATED

                // CHANGING THE COLOR TO INDICATE FAILURE
                request.setAttribute("loginFailed","Authentication Failed. Please Check Your Credentials");

                // REDIRECT TO LOGIN PAGE
                handlingForward(request, response, LOGIN_PAGE);
            }
        }

    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // SESSION RETRIEVAL: FALSE INDICATE NOT TO CREATE A NEW ONE IF SESSION DOES NOT EXIST
        HttpSession session = request.getSession(false);

        // CHECK IF SESSION EXISTS
        if(session != null){

            // CLOSING THE SESSION
            session.invalidate();
        }

        // REDIRECTING TO LOGIN PAGE
        handlingForward(request, response, LOGIN_PAGE);
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

    public void destroy() {
    }
}