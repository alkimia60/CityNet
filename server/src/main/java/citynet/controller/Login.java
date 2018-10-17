package citynet.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import citynet.Utils.AuthenticationUtils;
import citynet.Utils.TextUtils;
import citynet.dao.UserDao;
import citynet.Login.Tokens;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author diazg
 */
public class Login extends HttpServlet {

    private final long TOKEN_EXP_TIME = 900000;
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
        response.setContentType("application/json;charset=UTF-8");
        String reply = "";
        Tokens token = new Tokens();
        PrintWriter out = response.getWriter();
        try {
            String user = request.getParameter("user");
            String password = request.getParameter("password");
            if (ud.isValidUser(user)) {
                String hashedPassword = ud.findUserHashedPassword(user);
                AuthenticationUtils au = new AuthenticationUtils();
                if (au.checkPass(password, hashedPassword)) {
                    String rol = ud.findUserRol(user);
                    reply = TextUtils.jsonTokenRolMessage(token.createJWT(user, TOKEN_EXP_TIME), rol);
                } else {
                    reply = TextUtils.jsonErrorMessage("Invalid password");
                }
            } else {
                reply = TextUtils.jsonErrorMessage("Invalid user");
            }
        } catch (Exception e) {
            reply = TextUtils.jsonErrorMessage("Can't log user");
        } finally {
            out.append(reply);
            out.close();
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
