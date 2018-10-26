/*
 * @author Francisco Javier Diaz Garzon
 */
package citynet.controller;

import citynet.utils.AuthUtils;
import citynet.utils.TextUtils;
import citynet.dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Servlet to manage user log in
 */
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String reply;
        try (PrintWriter out = response.getWriter()) {
            String user = request.getParameter("user"); //email of the user
            String password = request.getParameter("password"); // password of the user
            UserDao ud = new UserDao();
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
