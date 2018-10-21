/*
 * @author Francisco Javier Diaz Garzon
 * Class to make http requests to the server
 */
package citynet.client;

import citynet.client.model.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

class ClientRequests {

    private static final String LOCAL_URL = "http://localhost:8084/citynet/UserManager";
    private static final String PUBLIC_URL = "http://ec2-35-180-7-53.eu-west-3.compute.amazonaws.com:8080/citynet/UserManager";
    private static final String LOCAL_LOGIN = "http://localhost:8084/citynet/Login";
    private static final String PUBLIC_LOGIN = "http://ec2-35-180-7-53.eu-west-3.compute.amazonaws.com:8080/citynet/Login";

    private static String token; //Session token
    private static String rol;  //User role

    public final static void main(String[] args) {

        ClientRequests cr = new ClientRequests();
        User user = new User("alex@usuari.com", "pass", "Alex",
                "Diaz", "Carrer Florida 30", "08016", "Barcelona ");

        User userUpdate = new User("diazgx@diba.cat", null, "Alejandro",
                "Diaz Garzon", "Av. Torras i Bages", " 08016", " Sta. Coloma de Gramenet ");

        //Donar d'alta un usuari com objecte user
        //cr.userRegister(PUBLIC_URL,user);
        //Login usuari
        cr.userLogin(PUBLIC_LOGIN, "diazgx@diba.cat", "pass");
        //List All Users
        cr.listAllUsers(PUBLIC_URL, token, 0);
        //Donar de baixa un usuari pel seu email
        //cr.userDelete(PUBLIC_URL, token, "alex@usuri.com");
        //Change Password
        //cr.changePassword(PUBLIC_URL, token, "old", "new");
        //Ask user profile
        //cr.askUserProfile(PUBLIC_URL, token);
        //Update user profile
        //cr.updateUserProfile(PUBLIC_URL, token, userUpdate);
        //List All Users filter admin, editor, user
        //si no és un dels tres valors retorna tots els usuaris
        //pot substituir a listAllUsers
        cr.listAllUsersFilter(PUBLIC_URL, token, 0, User.UL_EDITOR);
        //update user rol
        cr.updateUserRol(PUBLIC_URL, token, "alex@usuari.com", User.UL_EDITOR);

    }

    /**
     * Function to request the list of all users alphabetically ordered
     *
     * @param screen current screen number, starting with 0
     * @return String with startOfTable, endOfTable and the fields of the
     * records between #. All records separated by \n
     */
    private String listAllUsers(String url, String token, int screen) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ListAllUsers"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("screen", String.valueOf(screen)));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            //httpPost.addHeader("Authorization", token);

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error listAllUsers";
    }

    private String userRegister(String url, User user) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            Gson gson = new Gson();
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "UserRegister"));
            nvps.add(new BasicNameValuePair("user", gson.toJson(user)));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error userRegister";
    }

    private String userDelete(String url, String token, String userToDelete) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "UserDelete"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("user", userToDelete));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error userDelete";
    }

    private boolean userLogin(String url, String user, String password) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("user", String.valueOf(user)));
            nvps.add(new BasicNameValuePair("password", password));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println(responseBody);
            
            //Comprova que dins la resposta no hi hagi una clau "error"
            // i que sí hi hagi una clau token
            String error = JsonUtils.findJsonValue(responseBody, "error");
            if (("No json data".equals(error))
                    && (!"No json data".equals(JsonUtils.findJsonValue(responseBody, "token")))) {
                token = JsonUtils.findJsonValue(responseBody, "token");
                rol = JsonUtils.findJsonValue(responseBody, "rol");
                return true;

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

    private String changePassword(String url, String token, String oldPassword, String newPassword) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ChangePassword"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("oldPassword", oldPassword));
            nvps.add(new BasicNameValuePair("newPassword", newPassword));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error changePassword";
    }

    private String askUserProfile(String url, String token) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "AskUserProfile"));
            nvps.add(new BasicNameValuePair("token", token));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error askUserProfile";
    }

    private String updateUserProfile(String url, String token, User userToUpdate) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            Gson gson = new Gson();
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "UpdateUserProfile"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("user", gson.toJson(userToUpdate)));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error updateUserProfile";
    }

    private String listAllUsersFilter(String url, String token, int screen, String filter) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ListAllUsersFilter"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("screen", String.valueOf(screen)));
            nvps.add(new BasicNameValuePair("filter", filter));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            //httpPost.addHeader("Authorization", token);

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error listAllUsersFilter";
    }

    private String updateUserRol(String url, String token, String user, String newRol) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "UpdateUserRol"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("user", user));
            nvps.add(new BasicNameValuePair("rol", newRol));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());
            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error updateUserRol";
    }

    private ResponseHandler<String> customResponseHandler() {
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            @Override
            public String handleResponse(
                    final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
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

}
