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
import java.util.List;

public class UserManager extends HttpServlet {

    private static final int INCREASE = 10; //number of rows to return
    private final UserDao ud = new UserDao();

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

        if (request.getParameter("action") != null) {
            action = request.getParameter("action");
            switch (action) {
                case "ListAllUsers":
                    int screen = Integer.parseInt(request.getParameter("screen"));
                    response.setContentType("text/html;charset=UTF-8");
                    replyAllUsers(response, screen * INCREASE, INCREASE);
                    //replyAllUsersJson(response, screen * INCREASE, INCREASE);
                    break;
                default:
                    StringBuilder reply = new StringBuilder();
                    reply.append("No action");
                    PrintWriter out = response.getWriter();
                    out.append(reply);
                    out.close();
                    break;
            }
        } else {
            StringBuilder reply = new StringBuilder();
            reply.append("No request");
            PrintWriter out = response.getWriter();
            out.append(reply);
            out.close();
        }
    }

    private void replyAllUsers(HttpServletResponse response, int n, int m) throws IOException {
        StringBuilder reply = new StringBuilder();

        //Is the beginning of the table?
        if (n == 0) {
            reply.append("true\n");
        } else {
            reply.append("false\n");
        }

        //Is the end of the table?
        if (ud.totalUserRows() <= (n + m)) {
            reply.append("true\n");
        } else {
            reply.append("false\n");
        }

        List<User> users = ud.getAllUsers(n, m);
        for (User u : users) {
            reply.append("#").append(u.getEmail())
                    .append("#").append(u.getPassword())
                    .append("#").append(u.getName())
                    .append("#").append(u.getSurname())
                    .append("#").append(u.getAddress())
                    .append("#").append(u.getPostcode())
                    .append("#").append(u.getCity())
                    .append("#").append(u.getUserLevel()).append("#\n");
        }
        PrintWriter out = response.getWriter();
        out.append(reply);
        out.close();
    }

    private void replyAllUsersJson(HttpServletResponse response, int n, int m) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        StringBuilder reply = new StringBuilder();

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

        List<User> users = ud.getAllUsers(n, m);
        reply.append("\"users\":[");
        for (int i = 0; i < users.size(); i++) {
            if (i != 0) {
                reply.append(",");
            }
            reply.append("{\"id\":\"").append(users.get(i).getId()).append("\",")
                    .append("\"email\":\"").append(users.get(i).getEmail()).append("\",")
                    .append("\"password\":\"" + users.get(i).getPassword() + "\",")
                    .append("\"name\":\"" + users.get(i).getName() + "\",")
                    .append("\"surname\":\"" + users.get(i).getSurname() + "\",")
                    .append("\"address\":\"" + users.get(i).getAddress() + "\",")
                    .append("\"postcode\":\"" + users.get(i).getPostcode() + "\",")
                    .append("\"city\":\"" + users.get(i).getCity() + "\",")
                    .append("\"userLevel\":\"" + users.get(i).getUserLevel() + "\"}");
        }
        reply.append("]}");

        PrintWriter out = response.getWriter();
        out.append(reply);
        out.close();
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

}
