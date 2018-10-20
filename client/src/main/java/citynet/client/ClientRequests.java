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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
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
        User user = new User("gemma@rodriguez","pass" , "Gemma",
                "Rodriguez", "Av. Diagonal", "08030", "Barcelona ");

        //Donar d'alta un usuari com objecte user
        //cr.userRegister(user, PUBLIC_URL);
        //Login usuari
        cr.userLogin(PUBLIC_LOGIN, "diazgx@diba.cat", "xavu");
        //List All Users
        cr.listAllUsers(PUBLIC_URL, 0, token);
        //Donar de baixa un usuari pel seu email
        //cr.userDelete("gemma@rodriguez.com", PUBLIC_URL,token);
        //Change Password
        //cr.changePassword("xavi", "xavixavi", PUBLIC_URL, token);
        //Ask user profile
        cr.askUserProfile(PUBLIC_URL, token);
    }

    /**
     * Function to request the list of all users alphabetically ordered
     *
     * @param screen current screen number, starting with 0
     * @return String with startOfTable, endOfTable and the fields of the
     * records between #. All records separated by \n
     */
    private String listAllUsers(String url, int screen, String token) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ListAllUsers"));
            nvps.add(new BasicNameValuePair("screen", String.valueOf(screen)));
            nvps.add(new BasicNameValuePair("token", token));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            //httpPost.addHeader("Authorization", token);

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
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
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error listAllUsers";
    }

    private String userRegister(User user, String url) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            Gson gson = new Gson();
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("user", gson.toJson(user)));
            nvps.add(new BasicNameValuePair("action", "UserRegister"));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
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
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error userRegister";
    }

    private String userDelete(String userToDelete, String url, String token) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "UserDelete"));
            nvps.add(new BasicNameValuePair("user", userToDelete));
            nvps.add(new BasicNameValuePair("token", token));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
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
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error userRegister";
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
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println(responseBody);
            String error = JsonUtils.findJsonValue(responseBody, "error");

            if ((error == "No json data")
                    & (JsonUtils.findJsonValue(responseBody, "token") != "No json data")) {
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

    private String changePassword(String oldPassword, String newPassword, String url, String token) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ChangePassword"));
            nvps.add(new BasicNameValuePair("oldPassword", oldPassword));
            nvps.add(new BasicNameValuePair("newPassword", newPassword));
            nvps.add(new BasicNameValuePair("token", token));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
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
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error userRegister";
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
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error";
    }

}

