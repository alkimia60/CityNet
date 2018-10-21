/*
 * @author Francisco Javier Diaz Garzon
 * Main class to manage client requests
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
import citynet.token.TokenUtils;
import citynet.utils.AuthenticationUtils;

public class UserManager extends HttpServlet {

    private static final int LIST_INCREASE = 10; //number of rows to return
    private final UserDao ud = new UserDao();
    //StringUtils su = new TextUtils();

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

        String action;

        if (request.getParameter("action") != null) {
            action = request.getParameter("action");
            String token;
            switch (action) {
                case "ListAllUsers":
                    token = request.getParameter("token");
                    if (new TokenUtils().isValidToken(token)) {
                        int screen;
                        String screenStr = request.getParameter("screen");
                        if (TextUtils.isInteger(screenStr)) {
                            screen = Integer.parseInt(screenStr);
                            replyListAllUsers(response, screen * LIST_INCREASE, LIST_INCREASE, "%");
                        } else {
                            sendMessage(response, TextUtils.jsonErrorMessage("Param. screen format exception"));
                        }
                    } else {
                        sendMessage(response, TextUtils.jsonErrorMessage("Not a valid token"));
                    }
                    break;
                case "UserRegister":
                    replyUserRegister(response, request.getParameter("user"));
                    break;
                case "UserDelete":
                    token = request.getParameter("token");
                    if (new TokenUtils().isValidToken(token)) {
                        replyUserDelete(response, request.getParameter("user"));
                    } else {
                        sendMessage(response, TextUtils.jsonErrorMessage("Not a valid token"));
                    }
                    break;
                case "ChangePassword":
                    token = request.getParameter("token");
                    //Is valid token. Comprova si el token conté usuari i si l'usuari és a la BD
                    if (new TokenUtils().isValidToken(token)) {
                        String user = new TokenUtils().JWTTokenUser(token);
                        String oldPassword = request.getParameter("oldPassword");
                        String newPassword = request.getParameter("newPassword");
                        //Busca el hash del password de l'usuari a la BD
                        String hashedPassword = ud.findUserHashedPassword(user);
                        AuthenticationUtils au = new AuthenticationUtils();
                        //is valid password. Comprova el hash del password antic amb el de la BD
                        if (au.checkPass(oldPassword, hashedPassword)) {
                            replyChangePassword(response, user, newPassword);
                        } else {
                            sendMessage(response, TextUtils.jsonErrorMessage("Invalid password"));
                        }
                    } else {
                        sendMessage(response, TextUtils.jsonErrorMessage("Not a valid token"));
                    }
                    break;
                case "AskUserProfile":
                    token = request.getParameter("token");
                    if (new TokenUtils().isValidToken(token)) {
                        String user = new TokenUtils().JWTTokenUser(token); //Obté l'usuari del token
                        //Obté les dades de l'usuari
                        replyAskUserProfile(response, user);
                    } else {
                        sendMessage(response, TextUtils.jsonErrorMessage("Not a valid token"));
                    }
                    break;
                case "UpdateUserProfile":
                    token = request.getParameter("token");
                    if (new TokenUtils().isValidToken(token)) {
                        String user = new TokenUtils().JWTTokenUser(token); //Obté l'usuari del token
                        //Obté les dades a modificar de l'usuari
                        String userData = request.getParameter("user");
                        //Obté l'email identificador de l'usuari
                        String userToDelete = TextUtils.findJsonValue(userData, "email");
                        //Comprova si l'usuari a modificar és el mateix del token
                        if (!user.equals(userToDelete)) {
                            sendMessage(response, TextUtils.jsonErrorMessage("Wrong user"));
                        } else {
                            replyUpdateUserProfile(response, userData);
                        }
                    } else {
                        sendMessage(response, TextUtils.jsonErrorMessage("Not a valid token"));
                    }
                    break;
                case "UpdateUserRol":
                    token = request.getParameter("token");
                    if (new TokenUtils().isValidToken(token)) {
                        String user = request.getParameter("user");
                        if (!ud.isValidUser(user)) {
                            sendMessage(response, TextUtils.jsonErrorMessage("Not a valid user"));
                        } else {
                            String rol = request.getParameter("rol");
                            replyUpdateUserRol(response, user, rol);
                        }
                    } else {
                        sendMessage(response, TextUtils.jsonErrorMessage("Not a valid token"));
                    }
                    break;

                case "ListAllUsersFilter":
                    token = request.getParameter("token");
                    String filter = request.getParameter("filter");
                    if (new TokenUtils().isValidToken(token)) {
                        int screen;
                        String screenStr = request.getParameter("screen");
                        if (TextUtils.isInteger(screenStr)) {
                            screen = Integer.parseInt(screenStr);
                            replyListAllUsers(response, screen * LIST_INCREASE, LIST_INCREASE, filter);
                        } else {
                            sendMessage(response, TextUtils.jsonErrorMessage("Param. screen format exception"));
                        }
                    } else {
                        sendMessage(response, TextUtils.jsonErrorMessage("Not a valid token"));
                    }
                    break;

                default:
                    sendMessage(response, TextUtils.jsonErrorMessage("Param. action format exception"));
                    break;
            }
        } else {
            sendMessage(response, TextUtils.jsonErrorMessage("Incorrect parameters"));
        }
    }

    private void sendMessage(HttpServletResponse response, String message) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.append(message);
        }
    }

    private void replyListAllUsers(HttpServletResponse response, int n, int m, String filter) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        if (!filter.equals("admin") && !filter.equals("editor") && !filter.equals("user")) {
            filter = "%";
        }
        StringBuilder reply = new StringBuilder();

        List<User> users = ud.getAllUsers(n, m, filter);
        if (users.isEmpty()) {
            reply.append(TextUtils.jsonErrorMessage("No user results"));
        } else {
            //Is the beginning of the table?
            if (n == 0) {
                reply.append("{\"control\":[{\"start\":\"true\"},");
            } else {
                reply.append("{\"control\":[{\"start\":\"false\"},");
            }

            //Is the end of the table?
            if (ud.totalUserRows() <= (n + m)) {
                reply.append("{\"end\":\"true\"}],");
            } else {
                reply.append("{\"end\":\"false\"}],");
            }

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
//        PrintWriter out = response.getWriter();
//        out.append(reply);
//        out.close();
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

    private void replyUserRegister(HttpServletResponse response, String user) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        StringBuilder reply = new StringBuilder();
        reply.append(ud.userRegister(user));
        sendMessage(response, reply.toString());
    }

    private void replyUserDelete(HttpServletResponse response, String user) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        StringBuilder reply = new StringBuilder();
        reply.append(ud.userDelete(user));
        sendMessage(response, reply.toString());
    }

    private void replyChangePassword(HttpServletResponse response, String user, String password) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        StringBuilder reply = new StringBuilder();
        reply.append(ud.changePassword(user, password));
        sendMessage(response, reply.toString());
    }

    private void replyAskUserProfile(HttpServletResponse response, String user) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        StringBuilder reply = new StringBuilder();
        reply.append(gson.toJson(ud.findUserData(user)));
        sendMessage(response, reply.toString());
    }

    private void replyUpdateUserProfile(HttpServletResponse response, String user) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        StringBuilder reply = new StringBuilder();
        reply.append(ud.UpdateUserData(user));
        sendMessage(response, reply.toString());
    }

    private void replyUpdateUserRol(HttpServletResponse response, String user, String rol) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        StringBuilder reply = new StringBuilder();
        reply.append(ud.UpdateUserRol(user, rol));
        sendMessage(response, reply.toString());
    }
}
