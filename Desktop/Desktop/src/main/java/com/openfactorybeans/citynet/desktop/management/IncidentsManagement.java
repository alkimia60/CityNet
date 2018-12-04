package com.openfactorybeans.citynet.desktop.management;

import com.google.gson.Gson;
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
 * Classe per realitzar la gestió de incidències amb el servidor
 * @author Jose
 */
public class IncidentsManagement {
    
    /**
     * Demanem al servidor les dades de la inciència seleccionada
     * 
     * @param url URL del servidor
     * @param token Identificatiu de sessió iniciada
     * @param containerId Identificatiu del container
     * @return Ok o error
     */
    public String askIncident(String url, String token, String containerId) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ContainerIncident"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("containerId", containerId));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            //System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //System.out.println("----------------------------------------");
            //System.out.println(responseBody);
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(IncidentsManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error askContainerIncident";
    }
    
    /**
     * Notifiquem al servidor la incidència que s'ha tancat
     * 
     * @param url URL del servidor
     * @param token Identificatiu de sessió iniciada
     * @param incidentID Identificatiu de la incidència
     * @return Ok o error
     */
    public String incidentFinalize(String url, String token, int incidentID) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            //Gson object to convert Container Object into String
            //Gson gson = new Gson();
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "IncidentFinalize"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("incidentId", String.valueOf(incidentID)));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody; //Server response

        } catch (Exception ex) {
            Logger.getLogger(IncidentsManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error incidentFinalize";
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
    
}
