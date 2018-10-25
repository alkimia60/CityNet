package com.openfactorybeans.citynet.desktop.users;

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
 * Codificació canviar el rol d'un usuari a la base de dades del servidor
 * @author Jose
 */
public class UpdateUserRol {
    
    public String updateUserRol(String url, String token, String user, String newRol) {
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
            Logger.getLogger(UpdateUserRol.class.getName()).log(Level.SEVERE, null, ex);
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
