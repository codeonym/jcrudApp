package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.controllers;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
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

public class LoginController extends HttpServlet {

    // INITIALIZING THE VIEWS VARIABLES:
    private final String loginPage = "/WEB-INF/views/login.jsp";
    private final String homePage = "/WEB-INF/views/home.jsp";
    private final String errorPage = "/WEB-INF/views/errors.jsp";

    // INITIALIZING THE GLOBAL OBJS
    AuthService authService = new AuthService();

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

        //INSTANCING THE SESSION OBJECT
        HttpSession session = request.getSession();


        // GETTING THE USER FROM THE SESSION
        UserModel user = (UserModel) session.getAttribute("user");

        if(user != null){

            // USER IS ALREADY AUTHENTICATED

            // SESSION UPDATE
            SessionController.updateSession(request, response);

            handlingForward(request, response, homePage);

        }else {
            // USER NOT AUTHENTICATED
            // FORWARD TO LOGIN PAGE
            handlingForward(request, response, loginPage);
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
        String username,password;

        // GET USERNAME, PASSWORD
        username = request.getParameter("username");
        password = request.getParameter("password");

        // VALIDATING INPUT FIELDS
        if (username == null || password == null || username.isEmpty() || password.isEmpty()){

            // INVALID INPUT DETECTED => DO NOTHING
            // REDIRECTION TO LOGIN PAGE
            handlingForward(request, response, loginPage);
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
                user = authService.authentication(username, hashedPassword);
            } catch (SQLException e) {
                // SOMETHING WENT WRONG
                //DEBUGGING
                throw new RuntimeException(e);
            }
            if(user != null){

                // USER IS AUTHENTICATED
                // CREATING SESSION FOR USER
                session.setAttribute("user", user);

                // THE SESSION WILL TERMINATE AFTER 5MIN OF INACTIVITY
                session.setMaxInactiveInterval(60*5);

                // SESSION UPDATE
                SessionController.updateSession(request, response);

                // REDIRECTING  TO HOME PAGE
                handlingForward(request, response, homePage);
            }else {

                // NOT AUTHENTICATED
                // REDIRECT TO LOGIN PAGE
                handlingForward(request, response, loginPage);
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
        handlingForward(request, response, loginPage);
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