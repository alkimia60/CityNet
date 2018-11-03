/*
 * @author Francisco Javier Diaz Garzon
 */
package citynet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import citynet.dao.UserDao;
import citynet.model.User;
import citynet.utils.TextUtils;
import java.util.List;
import com.google.gson.Gson;
import citynet.utils.AuthUtils;

/**
 *
 * Servlet to manage User Object requests
 */
public class UserManager extends HttpServlet {

    private static final int LIST_INCREASE = 10; //number of rows to return
    private final UserDao ud = new UserDao();
    private String token;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action;//Explanation of the request

        if (request.getParameter("action") != null) {
            action = request.getParameter("action");
            switch (action) {
                case "ListAllUsersFilter":
                    replyListAllUsers(request, response);
                    break;
                case "UserRegister":
                    replyUserRegister(request, response);
                    break;
                case "UserDelete":
                    replyUserDelete(request, response);
                    break;
                case "ChangePassword":
                    replyChangePassword(request, response);
                    break;
                case "AskUserProfile":
                    replyAskUserProfile(request, response);
                    break;
                case "UpdateUserProfile":
                    replyUpdateUserProfile(request, response);
                    break;
                case "UpdateUserRol":
                    replyUpdateUserRol(request, response);
                    break;
                case "UserLogin":
                    replyUserLogin(request, response);
                    break;
                default:
                    sendMessage(response, TextUtils.jsonErrorMessage("Unknown action value"));
                    break;
            }
        } else {
            sendMessage(response, TextUtils.jsonErrorMessage("Null action parameter"));
        }
    }

    /**
     * Sends resonse to client
     *
     * @param response servlet response
     * @param message String to return
     * @throws IOException
     */
    private void sendMessage(HttpServletResponse response, String message) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.append(message);
        }
    }

    /**
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    private void replyListAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        token = request.getParameter("token");
        String filter = request.getParameter("filter");
        if (filter == null) {//If there is no filter parameter, the filter = "%"
            filter = "%";
        }
        if (AuthUtils.isValidToken(token)) {//Checks if token is valid and token user is in DB
            int screen;
            String screenStr = request.getParameter("screen");//Current screen
            if (TextUtils.isInteger(screenStr)) {//Checks if screen value is int
                screen = Integer.parseInt(screenStr);//Parse to int
                response.setContentType("application/json;charset=UTF-8");
                //If filter is not admin or editor or user, filter = "%"
                if (!filter.equals("admin") && !filter.equals("editor") && !filter.equals("user")) {
                    filter = "%";
                }
                int n = screen * LIST_INCREASE;//Cursor position in the table
                int m = LIST_INCREASE;//Number of rows to return
                //Gets List<User> of 10 users
                List<User> users = ud.getAllUsers(n, m, filter);
                StringBuilder reply = new StringBuilder();
                if (users.isEmpty()) {
                    reply.append(TextUtils.jsonErrorMessage("No user results"));
                } else {//json with control and users elements
                    //Is the beginning of the table?
                    if (n == 0) {
                        reply.append("{\"control\":[{\"start\":\"true\"},");
                    } else {
                        reply.append("{\"control\":[{\"start\":\"false\"},");
                    }
                    //Is the end of the table?
                    if (ud.totalUserRows(filter) <= (n + m)) {
                        reply.append("{\"end\":\"true\"}],");
                    } else {
                        reply.append("{\"end\":\"false\"}],");
                    }
                    //Json users element
                    reply.append("\"users\":[");
                    for (int i = 0; i < users.size(); i++) {
                        if (i != 0) {
                            reply.append(",");
                        }
                        reply.append("{\"email\":\"").append(users.get(i).getEmail()).append("\",")
                                .append("\"name\":\"" + users.get(i).getName() + "\",")
                                .append("\"surname\":\"" + users.get(i).getSurname() + "\",")
                                .append("\"address\":\"" + users.get(i).getAddress() + "\",")
                                .append("\"postcode\":\"" + users.get(i).getPostcode() + "\",")
                                .append("\"city\":\"" + users.get(i).getCity() + "\",")
                                .append("\"userLevel\":\"" + users.get(i).getUserLevel() + "\"}");
                    }
                    reply.append("]}");
                }
                sendMessage(response, reply.toString());

            } else {
                sendMessage(response, TextUtils.jsonErrorMessage("Param. screen format exception"));
            }
        } else {
            sendMessage(response, TextUtils.jsonErrorMessage("Not a valid token"));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Register new user in DB
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    private void replyUserRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userEmail = TextUtils.findJsonValue(request.getParameter("user"), "email");
        if (ud.isUserInDB(userEmail)) {//Checks if user is in DB
            sendMessage(response, TextUtils.jsonErrorMessage("User already exists"));
        } else {
            response.setContentType("application/json;charset=UTF-8");
            StringBuilder reply = new StringBuilder();
            reply.append(ud.userRegister(request.getParameter("user")));//Register new user in DB
            sendMessage(response, reply.toString());
        }
    }

    /**
     * //Delete user in DB
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    private void replyUserDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        token = request.getParameter("token");
        if (AuthUtils.isValidToken(token)) {//Checks if token is valid and token user is in DB
            response.setContentType("application/json;charset=UTF-8");
            StringBuilder reply = new StringBuilder();
            reply.append(ud.userDelete(request.getParameter("user")));//Delete user in DB
            sendMessage(response, reply.toString());
        } else {
            sendMessage(response, TextUtils.jsonErrorMessage("Not a valid token"));
        }
    }

    /**
     * //Updates logged user password in DB
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    private void replyChangePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        token = request.getParameter("token");
        if (AuthUtils.isValidToken(token)) {//Checks if token is valid and token user is in DB
            String user = AuthUtils.JWTTokenUser(token);//Gets token user
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            if (newPassword.trim().isEmpty()) {
                sendMessage(response, TextUtils.jsonErrorMessage("New password is empty"));
            } else {
                //Searchs the password hash of the user in the BD
                String hashedPassword = ud.findUserHashedPassword(user);//Search the password hash in DB
                //Checks the hash of the old password with the BD
                if (AuthUtils.checkPass(oldPassword, hashedPassword)) {//Checks if the passwords match
                    response.setContentType("application/json;charset=UTF-8");
                    StringBuilder reply = new StringBuilder();
                    reply.append(ud.changePassword(user, newPassword));//Updates password in DB
                    sendMessage(response, reply.toString());
                } else {
                    sendMessage(response, TextUtils.jsonErrorMessage("Password does not match"));
                }
            }

        } else {
            sendMessage(response, TextUtils.jsonErrorMessage("Not a valid token"));
        }

    }

    /**
     * Gets logged user profile data in json format
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    private void replyAskUserProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        token = request.getParameter("token");
        if (AuthUtils.isValidToken(token)) {//Checks if token is valid and token user is in DB
            String user = AuthUtils.JWTTokenUser(token); //Gets token user
            response.setContentType("application/json;charset=UTF-8");
            Gson gson = new Gson();
            StringBuilder reply = new StringBuilder();
            reply.append(gson.toJson(ud.findUserData(user)));//Gets user profile data in json format
            sendMessage(response, reply.toString());
        } else {
            sendMessage(response, TextUtils.jsonErrorMessage("Not a valid token"));
        }
    }

    /**
     * Updates logged user profile in DB
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    private void replyUpdateUserProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        token = request.getParameter("token");
        if (AuthUtils.isValidToken(token)) {//Checks if token is valid and token user is in DB
            String user = AuthUtils.JWTTokenUser(token); //Gets token user
            //Gets user data to update
            String userData = request.getParameter("user");
            //Gets mail of profile user to update
            String userToDelete = TextUtils.findJsonValue(userData, "email");
            //Checks if user to update and token user match
            if (!user.equals(userToDelete)) {
                sendMessage(response, TextUtils.jsonErrorMessage("Logged user different to profile user"));
            } else {
                response.setContentType("application/json;charset=UTF-8");
                StringBuilder reply = new StringBuilder();
                reply.append(ud.UpdateUserData(userData));//Updates user profile
                sendMessage(response, reply.toString());
            }
        } else {
            sendMessage(response, TextUtils.jsonErrorMessage("Not a valid token"));
        }

    }

    /**
     * Updates user role in DB
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    private void replyUpdateUserRol(HttpServletRequest request, HttpServletResponse response) throws IOException {
        token = request.getParameter("token");
        if (AuthUtils.isValidToken(token)) {//Checks if token is valid and token user is in DB
            String user = request.getParameter("user");
            if (!ud.isUserInDB(user)) {//Checks if requested user is in DB
                sendMessage(response, TextUtils.jsonErrorMessage("Not a valid user"));
            } else {
                String rol = request.getParameter("rol");
                response.setContentType("application/json;charset=UTF-8");
                StringBuilder reply = new StringBuilder();
                reply.append(ud.UpdateUserRol(user, rol));//Updates user role
                sendMessage(response, reply.toString());
            }
        } else {
            sendMessage(response, TextUtils.jsonErrorMessage("Not a valid token"));
        }
    }

    private void replyUserLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String reply;
        try (PrintWriter out = response.getWriter()) {
            String user = request.getParameter("user"); //email of the user
            String password = request.getParameter("password"); // password of the user
            //UserDao ud = new UserDao();
            if (ud.isUserInDB(user)) { //Checks if the user is in the DB
                String hashedPassword = ud.findUserHashedPassword(user); //Search the password hash in db
                if (AuthUtils.checkPass(password, hashedPassword)) { //Checks if the passwords match
                    String rol = ud.findUserRol(user); //Search the user role in db
                    //Returns json with session token and user role
                    reply = TextUtils.jsonTokenRolMessage(AuthUtils.createJWT(user, AuthUtils.TOKEN_EXP_TIME), rol);
                } else {
                    reply = TextUtils.jsonErrorMessage("Invalid password"); //If the passwords dont match returns error
                }
            } else {
                reply = TextUtils.jsonErrorMessage("User does not exist");//If the user isnot in db returns error
            }
            out.append(reply);

        }
    }
}
