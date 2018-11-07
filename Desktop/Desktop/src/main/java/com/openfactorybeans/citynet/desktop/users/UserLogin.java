package com.openfactorybeans.citynet.desktop.users;

import com.openfactorybeans.citynet.desktop.forms.Login;
import com.openfactorybeans.citynet.desktop.utils.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 * Codificació d'identificació d'un usuari a la base de dades del servidor per poder accedir a l'aplicació
 * 
 * @author Jose
 */
public class UserLogin {
    
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
            
            System.out.println("Login - Server message: " + serverMessage);

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
            Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
