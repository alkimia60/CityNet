package com.openfactorybeans.citynet.desktop.management;

import com.google.gson.Gson;
import com.openfactorybeans.citynet.desktop.forms.Login;
import com.openfactorybeans.citynet.desktop.model.User;
import com.openfactorybeans.citynet.desktop.utils.JsonUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 * Classe per realitzar la gestió d'usuaris amb el servidor
 * @author Jose
 */
public class UsersManagement {

    /**
     * Realitza una connexió al servidor per poder accedir a l'aplicació
     * @param url URL del servidor
     * @param user Usuari que s'identifica
     * @param password La contrasenya de l'usuari
     * @return true si està logejat o false si no ho està
     */
    public boolean userLogin(String url, String user, String password) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "UserLogin"));
            nvps.add(new BasicNameValuePair("user", String.valueOf(user)));
            nvps.add(new BasicNameValuePair("password", password)); 
            
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));


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
            //System.out.println(responseBody);
            String serverMessage = JsonUtils.findJsonValue(responseBody, "error");
            
            //System.out.println("Login - Server message: " + serverMessage);

            if ((serverMessage == null)
                    & (JsonUtils.findJsonValue(responseBody, "token") != "No json data")) {
                Login.token = JsonUtils.findJsonValue(responseBody, "token");
                Login.rol = JsonUtils.findJsonValue(responseBody, "rol");
                return true;

            } else {
                Login.token = null;
                Login.token = null;
                return false;
            }

        } catch (Exception ex) {
            Logger.getLogger(UsersManagement.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean userLoginSSL(String url, String user, String password) {
        try {
            SSLContext sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, (certificate, authType) -> true).build();
            CloseableHttpClient httpclient = HttpClients.custom().setSSLContext(sslContext)
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
                    .build();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "UserLogin"));
            nvps.add(new BasicNameValuePair("user", String.valueOf(user)));
            nvps.add(new BasicNameValuePair("password", password)); 
            
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));


            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //Check if response is a redirection and the re-execute request
            responseBody = reExecuteIfRedirected(responseBody, httpPost, httpclient);
            
            System.out.println(responseBody);
            String serverMessage = JsonUtils.findJsonValue(responseBody, "error");
            
            //System.out.println("Login - Server message: " + serverMessage);

            if ((serverMessage == null)
                    & (JsonUtils.findJsonValue(responseBody, "token") != "No json data")) {
                Login.token = JsonUtils.findJsonValue(responseBody, "token");
                Login.rol = JsonUtils.findJsonValue(responseBody, "rol");
                return true;

            } else {
                Login.token = null;
                Login.token = null;
                return false;
            }

        } catch (Exception ex) {
            Logger.getLogger(UsersManagement.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Realitza una connexió al servidor per realitzar el registre d'un nou usuari
     * @param user Usuari a registrar les seves dades
     * @param url URL del servidor
     * @return 
     */
    public String userRegister(User user, String url) {
        
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            Gson gson = new Gson();
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("user", gson.toJson(user)));
            nvps.add(new BasicNameValuePair("action", "UserRegister"));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            //System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //System.out.println("----------------------------------------");
            //System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(UsersManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error userRegister";
    }
    
    /**
     * Mètode per obtenir un llistat d'usuaris
     * 
     * @param url URL del servidor
     * @param token Identificatiu de sessió iniciada
     * @param screen Número de pàgina sol·licitada al servidor. 0 és la primera
     * @param filter Valor del filtre
     * @return 
     */
    public String usersList(String url, String token, int screen, String filter) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ListAllUsersFilter"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("screen", String.valueOf(screen)));
            nvps.add(new BasicNameValuePair("filter", filter));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            //System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //System.out.println("----------------------------------------");
            //System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(UsersManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error listAllUsersFilter";
    }
    
    /**
     * Realitza una connexió al servidor per actualitzar el rol d'un usuari
     * @param url URL del servidor
     * @param token Identificatiu de sessió iniciada
     * @param user Usuari al que s'actualitza el rol
     * @param newRol Nou rol asignat a l'usuari
     * @return 
     */
    public String userUpdateRol(String url, String token, String user, String newRol) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "UpdateUserRol"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("user", user));
            nvps.add(new BasicNameValuePair("rol", newRol));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //System.out.println("----------------------------------------");
            //System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(UsersManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error updateUserRol";
    }
    
    /**
     * Fa una connexió al servidor per eliminar el registre d'un usuari
     * @param url URL del servidor
     * @param token Identificatiu de sessió iniciada
     * @param userToDelete usuari a eliminar de la base de dades
     * @return 
     */
    public String userDelete(String url, String token, String userToDelete) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "UserDelete"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("user", userToDelete));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));


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
            //System.out.println("----------------------------------------");
            //System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(UsersManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error userRegister";
    }
    
    /**
     * Demanem al servidor les dades de l'usuari autenticat
     * @param url URL del servidor
     * @param token Identificatiu de sessió iniciada
     * @return Ok o error
     */
    public String askUserProfile(String url, String token) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "AskUserProfile"));
            nvps.add(new BasicNameValuePair("token", token));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            //System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //System.out.println("----------------------------------------");
            //System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(UsersManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error askUserProfile";
    }
    
    public String updateUserProfile(String url, String token, User userToUpdate) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            Gson gson = new Gson();
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "UpdateUserProfile"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("user", gson.toJson(userToUpdate)));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            //System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //System.out.println("----------------------------------------");
            //System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(UsersManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error updateUserProfile";
    }
    
    /**
     * Fa una connexió al servidor per modificar la conrasenya de l'usuari amb sessió iniciada
     * @param url URL del servidor
     * @param token Identificatiu de sessió iniciada
     * @param oldPassword Contrasenya antiga
     * @param newPassword Contrasenya nova
     * @return Ok o error
     */
    public String userChangePassword(String url, String token, String oldPassword, String newPassword) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ChangePassword"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("oldPassword", oldPassword));
            nvps.add(new BasicNameValuePair("newPassword", newPassword));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //System.out.println("----------------------------------------");
            //System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(UsersManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error changePassword";
    }
    
    /**
     * Mètode per crear un controlador de resposta personalitzat per a les sol·licituds
     * 
     * @return Controlador de resposta personalitzat
     */
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
