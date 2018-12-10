/*
 * @author Francisco Javier Diaz Garzon
 * Class to make http requests to the server
 */
package citynet.SSLClient;

import citynet.client.model.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

class ClientRequests {

    private static final String LOCAL_SSL_URL = "https://localhost:8443";
    private static final String LOCAL_URL = "http://localhost:8084";
    private static final String PUBLIC_SSL_URL = "https://ec2-35-180-7-53.eu-west-3.compute.amazonaws.com:8443";
    private static final String PUBLIC_URL = "http://ec2-35-180-7-53.eu-west-3.compute.amazonaws.com:8080";
    private static final String URL = PUBLIC_URL;
    private static final String URI = URL + "/citynetSSL/UserManager";

    public static String token; //Session token
    public static String rol;  //User role

    public final static void main(String[] args) {
        try {
            SSLContext sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, (certificate, authType) -> true).build();
            CloseableHttpClient httpclient = HttpClients.custom().setSSLContext(sslContext)
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
                    //.setRedirectStrategy(new LaxRedirectStrategy()) //Redirecció automàtica
                    .build();
            HttpPost httpPost = new HttpPost(URI);

            ClientRequests cr = new ClientRequests();
            // User object to use in requests
            User user = new User("diazgx9@diba", "P4$$w0rd", "Javier",
                    "Diaz", "Carrer Florida 30", "08016", "Barcelona ");
            // User object to use in profile update requests
            User userUpdate = new User("diazgx9@diba.cat", null, "Pepito",
                    "Diaz Garzon", "Av. Torras i Bages", " 08016", " Sta. Coloma de Gramenet ");
            //All functions tests:
            //Register a user as a user object
            cr.userRegister(httpclient, httpPost, user);
            //User login
            cr.userLogin(httpclient, httpPost, "diazgx@diba.cat", "X4vix@vi");
            //Unsubscribe a user by email
            //cr.userDelete(httpclient, httpPost, token, "diazgx9@diba.cat");
            //Change Password
            //cr.changePassword(httpclient, httpPost, token, "pass", "nou");
            //Ask user profile
            //cr.askUserProfile(httpclient, httpPost, token);
            //Update user profile
            //cr.updateUserProfile(httpclient, httpPost, token, userUpdate);
            //List All Users filter admin, editor or user
            //If it is not one of the three values, all users return
            cr.listAllUsersFilter(httpclient, httpPost, token, 0, "");
            //Update user rol
            //cr.updateUserRol(httpclient, httpPost, token, "diazgx9@diba.cat", User.UL_EDITOR);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ContainerRequests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(ContainerRequests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(ContainerRequests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Function to request request 10 rows of all users alphabetically ordered
     *
     * @param httpclient
     * @param httpPost
     * @param token session token
     * @param screen current application screen number, starting with 0
     * @return json String with elements startOfTable, endOfTable and the user
     * objects
     */
    private String listAllUsers(CloseableHttpClient httpclient, HttpPost httpPost, String token, int screen) {
        try {
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ListAllUsers"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("screen", String.valueOf(screen)));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //Checks if response is a redirection and then re-execute request            
            responseBody = reExecuteIfRedirected(responseBody, httpPost, httpclient);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody; //Server response

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error listAllUsers";
    }

    /**
     * Function to request the registration of a user
     *
     * @param httpclient
     * @param httpPost
     * @param user User object to register
     * @return json String with elements "ok" or "error"
     */
    private String userRegister(CloseableHttpClient httpclient, HttpPost httpPost, User user) {
        try {
            //Gson object to convert User Object into String
            Gson gson = new Gson();
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "UserRegister"));
            nvps.add(new BasicNameValuePair("user", gson.toJson(user)));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //Checks if response is a redirection and then re-execute request            
            responseBody = reExecuteIfRedirected(responseBody, httpPost, httpclient);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody; //Server response

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error userRegister";
    }

    /**
     * Function to request the removal of a user
     *
     * @param httpclient
     * @param httpPost
     * @param token session token
     * @param userToDelete email of the user to delete
     * @return json String with elements "ok" or "error"
     */
    private String userDelete(CloseableHttpClient httpclient, HttpPost httpPost, String token, String userToDelete) {
        try {
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "UserDelete"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("user", userToDelete));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //Checks if response is a redirection and then re-execute request            
            responseBody = reExecuteIfRedirected(responseBody, httpPost, httpclient);
            System.out.println("----------------------------------------");
            System.out.println(responseBody); //Server response
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error userDelete";
    }

    /**
     * Function to request the log in of a user and initialize token and rol
     * variables
     *
     * @param httpclient
     * @param httpPost
     * @param user email of the user to login
     * @param password password of the user to login
     * @return boolean true of false whether log in or not
     */
    public boolean userLogin(CloseableHttpClient httpclient, HttpPost httpPost, String user, String password) {
        try {
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            //Login in UserManager. url must be PUBLIC_URL
            nvps.add(new BasicNameValuePair("action", "UserLogin"));
            nvps.add(new BasicNameValuePair("user", String.valueOf(user)));
            nvps.add(new BasicNameValuePair("password", password));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //Checks if response is a redirection and then re-execute request            
            responseBody = reExecuteIfRedirected(responseBody, httpPost, httpclient);
            System.out.println(responseBody);//Server response

            //Check that there is no "error" key in the answer
            // and that there is a "token" key
            String error = JsonUtils.findJsonValue(responseBody, "error");
            if (("No json data".equals(error))
                    && (!"No json data".equals(JsonUtils.findJsonValue(responseBody, "token")))) {
                token = JsonUtils.findJsonValue(responseBody, "token");
                rol = JsonUtils.findJsonValue(responseBody, "rol");
                return true;
                //If there is an error or there is no token the variables are null
            } else {
                token = null;
                rol = null;
                return false;
            }

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Function to request the change of the current logged user password
     *
     * @param httpclient
     * @param httpPost
     * @param token session token
     * @param oldPassword
     * @param newPassword
     * @return json String with elements "ok" or "error"
     */
    private String changePassword(CloseableHttpClient httpclient, HttpPost httpPost, String token, String oldPassword, String newPassword) {
        try {
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ChangePassword"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("oldPassword", oldPassword));
            nvps.add(new BasicNameValuePair("newPassword", newPassword));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //Checks if response is a redirection and then re-execute request            
            responseBody = reExecuteIfRedirected(responseBody, httpPost, httpclient);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);//Server response
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error changePassword";
    }

    /**
     * Function to request the current logged user profile
     *
     * @param httpclient
     * @param httpPost
     * @param token session token
     * @return json String with elements user profile or "error"
     */
    private String askUserProfile(CloseableHttpClient httpclient, HttpPost httpPost, String token) {
        try {
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "AskUserProfile"));
            nvps.add(new BasicNameValuePair("token", token));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //Checks if response is a redirection and then re-execute request            
            responseBody = reExecuteIfRedirected(responseBody, httpPost, httpclient);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);//Server response
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error askUserProfile";
    }

    /**
     * Function to request the current logged user profile update
     *
     * @param httpclient
     * @param httpPost
     * @param token session token
     * @param userToUpdate User Object with the new profile data
     * @return json String with elements "ok" or "error"
     */
    private String updateUserProfile(CloseableHttpClient httpclient, HttpPost httpPost, String token, User userToUpdate) {
        try {
            //Gson object to convert User Object into String
            Gson gson = new Gson();
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "UpdateUserProfile"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("user", gson.toJson(userToUpdate)));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //Checks if response is a redirection and then re-execute request            
            responseBody = reExecuteIfRedirected(responseBody, httpPost, httpclient);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);//Server response
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error updateUserProfile";
    }

    /**
     * Function to request 10 rows of all users alphabetically ordered filtered
     * by user role
     *
     * @param httpclient
     * @param httpPost
     * @param token session token
     * @param screen current application screen number, starting with 0
     * @param filter User role for which you want to filter ("admin", "editor",
     * "user" or any word for all users)
     * @return json String with elements startOfTable, endOfTable and the user
     * objects
     */
    private String listAllUsersFilter(CloseableHttpClient httpclient, HttpPost httpPost, String token, int screen, String filter) {
        try {
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ListAllUsersFilter"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("screen", String.valueOf(screen)));
            nvps.add(new BasicNameValuePair("filter", filter));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //Checks if response is a redirection and then re-execute request            
            responseBody = reExecuteIfRedirected(responseBody, httpPost, httpclient);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);//Server response
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error listAllUsersFilter";
    }

    /**
     * Function to request the role update of a user
     *
     * @param httpclient
     * @param httpPost
     * @param token session token
     * @param user User to update role
     * @param newRol
     * @return json String with elements "ok" or "error"
     */
    private String updateUserRol(CloseableHttpClient httpclient, HttpPost httpPost, String token, String user, String newRol) {
        try {
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "UpdateUserRol"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("user", user));
            nvps.add(new BasicNameValuePair("rol", newRol));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());
            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //Checks if response is a redirection and then re-execute request            
            responseBody = reExecuteIfRedirected(responseBody, httpPost, httpclient);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);//Server response
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error updateUserRol";
    }

    /**
     * Function to create a custom response handler for the requests
     *
     * @return custom response handler
     */
    private ResponseHandler<String> customResponseHandler() {
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(
                    final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status == 302) {//If response status is a redirection
                    String newURL = response.getHeaders("location")[0].getValue();
                    return "Status 302:" + newURL; //return error code and new url to re-execute request
                }
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
        return responseHandler;
    }

    /**
     * Re-execute request when status of response is 302 (redirected)
     *
     * @param responseBody response with status code and new URL
     * @param httpPost
     * @param httpclient
     * @return redirected resposeBody
     * @throws URISyntaxException
     * @throws IOException
     */
    private String reExecuteIfRedirected(String responseBody, HttpPost httpPost, CloseableHttpClient httpclient) throws URISyntaxException, IOException {
        //If there is a redirection, re-execute request with new URI
        if ("Status 302:".equals(responseBody.substring(0, 11))) {
            String newURL = responseBody.substring(11);
            httpPost.setURI(new URI(newURL));
            System.out.println("Executing request " + httpPost.getRequestLine());
            responseBody = httpclient.execute(httpPost, customResponseHandler());
        }
        return responseBody;
    }

}
