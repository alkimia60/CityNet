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
import citynet.Utils.StringUtils;
import java.util.List;
import com.google.gson.Gson;

public class UserManager extends HttpServlet {

    private static final int LIST_INCREASE = 10; //number of rows to return
    private final UserDao ud = new UserDao();
    //StringUtils su = new StringUtils();

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

        String action = "";
        //StringUtils su = new StringUtils();

        if (request.getParameter("action") != null) {
            action = request.getParameter("action");
            switch (action) {
                case "ListAllUsers":
                    int screen = 0;
                    String screenStr = request.getParameter("screen");
                    if (StringUtils.isInteger(screenStr)) {
                        screen = Integer.parseInt(screenStr);
                        //response.setContentType("text/html;charset=UTF-8");
                        replyListAllUsers(response, screen * LIST_INCREASE, LIST_INCREASE);
                    } else {
                        sendMessage(response, StringUtils.jsonErrorMessage("Param. screen format exception"));
                    }
                    break;
                case "UserRegister":
                    //Gson gson = new Gson();
                    //User user = (User) gson.fromJson(request.getParameter("user"), User.class);
                    replyUserRegister(response, request.getParameter("user"));
                    break;
                case "UserDelete":
                    //Gson gson = new Gson();
                    //User user = (User) gson.fromJson(request.getParameter("user"), User.class);
                    replyUserDelete(response, request.getParameter("user"));
                    break;
                case "Login":
                    //Gson gson = new Gson();
                    //User user = (User) gson.fromJson(request.getParameter("user"), User.class);
                    replyLogin(response, request.getParameter("user"), request.getParameter("password"));
                    break;
                default:
                    sendMessage(response, StringUtils.jsonErrorMessage("Param. action format exception"));
                    break;
            }
        } else {
            sendMessage(response, StringUtils.jsonErrorMessage("Incorrect parameters"));
        }
    }

    private void sendMessage(HttpServletResponse response, String message) throws IOException {
//        StringBuilder reply = new StringBuilder();
//        reply.append(message);
        PrintWriter out = response.getWriter();
        out.append(message);
        out.close();
    }

    private void replyListAllUsers(HttpServletResponse response, int n, int m) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        StringBuilder reply = new StringBuilder();

        List<User> users = ud.getAllUsers(n, m);
        if (users.isEmpty()) {
            //StringUtilsStringUtils su = new StringUtils();
            reply.append(StringUtils.jsonErrorMessage("No user results"));
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

    private void replyLogin(HttpServletResponse response, String user, String password) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        StringBuilder reply = new StringBuilder();
        reply.append(ud.userLogin(user, password));
        sendMessage(response, reply.toString());
    }

}
