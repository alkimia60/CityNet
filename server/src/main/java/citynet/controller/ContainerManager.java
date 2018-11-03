/*
 * @author Francisco Javier Diaz Garzon
 * Servlet to manage ContainerManager Object requests
 */
package citynet.controller;

import citynet.dao.ContainerDao;
import citynet.model.Container;
import citynet.model.User;
import citynet.utils.AuthUtils;
import citynet.utils.TextUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContainerManager extends HttpServlet {

    private static final int LIST_INCREASE = 10; //number of rows to return
    private final ContainerDao cd = new ContainerDao();
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
                case "ContainerRegister":
                    replyContainerRegister(request, response);
                    break;
                case "ListAllContainers":
                    replyListAllContainers(request, response);
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
     * Register new container in DB
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    private void replyContainerRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        token = request.getParameter("token");
        if (AuthUtils.isValidToken(token)) {//Checks if token is valid and token user is in DB
            String idContainer = TextUtils.findJsonValue(request.getParameter("container"), "id");
            idContainer.toUpperCase();//Convert to uppercase
            if (idContainer.length() == 6) {//Container id  must be 6 alphanumeric characters
                if (!cd.isContainerInDB(idContainer)) {//Checks if container exists in DB
                    response.setContentType("application/json;charset=UTF-8");
                    StringBuilder reply = new StringBuilder();
                    reply.append(cd.containerRegister(request.getParameter("container")));//Register new container in DB
                    sendMessage(response, reply.toString());
                } else {
                    sendMessage(response, TextUtils.jsonErrorMessage("Container already exists"));
                }
            } else {
                sendMessage(response, TextUtils.jsonErrorMessage("container.id  must be 6 alphanumeric characters"));
            }
        } else {
            sendMessage(response, TextUtils.jsonErrorMessage("Not a valid token"));
        }
    }

    /**
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    private void replyListAllContainers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        token = request.getParameter("token");
        String filterField = request.getParameter("filterField");
        String filterValue = request.getParameter("filterValue");
        if ((filterField == null) || !cd.isFieldInDB("container", filterField)) {//If there is no filter field or is not valid, the filterField = "1"
            filterField = "1";
            filterValue = "1";
        } else {
            //If value of filter is not valid, value = "%"
            if (!cd.isValueInField("container", filterField, filterValue)) {
                filterValue = "%";
            }
        }
        if (AuthUtils.isValidToken(token)) {//Checks if token is valid and token user is in DB
            int screen;
            String screenStr = request.getParameter("screen");//Current screen
            if (TextUtils.isInteger(screenStr)) {//Checks if screen value is int
                screen = Integer.parseInt(screenStr);//Parse to int
                response.setContentType("application/json;charset=UTF-8");

                int n = screen * LIST_INCREASE;//Cursor position in the table
                int m = LIST_INCREASE;//Number of rows to return
                //Gets List<Container> of 10 users
                List<Container> containers = cd.getAllContainers(n, m, filterField, filterValue);
                StringBuilder reply = new StringBuilder();
                if (containers.isEmpty()) {
                    reply.append(TextUtils.jsonErrorMessage("No containers results"));
                } else {//json with control and users elements
                    //Is the beginning of the table?
                    if (n == 0) {
                        reply.append("{\"control\":[{\"start\":\"true\"},");
                    } else {
                        reply.append("{\"control\":[{\"start\":\"false\"},");
                    }
                    //Is the end of the table?
                    if (cd.totalContainerRows(filterField, filterValue) <= (n + m)) {
                        reply.append("{\"end\":\"true\"}],");
                    } else {
                        reply.append("{\"end\":\"false\"}],");
                    }
                    //Json users element
                    reply.append("\"containers\":[");
                    for (int i = 0; i < containers.size(); i++) {
                        if (i != 0) {
                            reply.append(",");
                        }
                        reply.append("{\"id\":\"").append(containers.get(i).getId()).append("\",")
                                .append("\"type\":\"" + containers.get(i).getType() + "\",")
                                .append("\"latitude\":\"" + containers.get(i).getLatitude() + "\",")
                                .append("\"longitude\":\"" + containers.get(i).getLongitude() + "\",")
                                .append("\"operative\":\"" + containers.get(i).isOperative() + "\",")
                                .append("\"active_incident\":\"" + containers.get(i).getActive_incident() + "\"}");
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

}
