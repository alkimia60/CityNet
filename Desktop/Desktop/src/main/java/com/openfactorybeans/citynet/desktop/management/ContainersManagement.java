package com.openfactorybeans.citynet.desktop.management;

import com.google.gson.Gson;
import com.openfactorybeans.citynet.desktop.model.Container;
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
 * Classe per realitzar la gestió de contenidors amb el servidor
 * @author Jose
 */
public class ContainersManagement {
    
    /**
     * Fa una connexió al servidor per realitzar el registre d'un nou contenidor
     * @param url URL del servidor
     * @param token Identificatiu de sessió iniciada
     * @param container Contenidor a registrar les seves dades
     * @return 
     */    
    public String containerRegister(String url, String token, Container container) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            
            //Gson object to convert Container Object into String
            Gson gson = new Gson();
            
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ContainerRegister"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("container", gson.toJson(container)));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            //System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //System.out.println("----------------------------------------");
            //System.out.println(responseBody);
            return responseBody; //Server response

        } catch (Exception ex) {
            Logger.getLogger(ContainersManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error containerRegister";
    }
    
    /**
     * Mètode per obtenir un llistat de contenidors
     * 
     * @param url URL del servidor
     * @param token Identificatiu de sessió iniciada
     * @param screen Número de pàgina sol·licitada al servidor. 0 és la primera
     * @param filterField Camp pel qual es vol filtrar
     * @param filterValue Valor del filtre
     * @return La resposta del servidor
     */
    public String listAllContainers(String url, String token, int screen, String filterField, String filterValue) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ListAllContainers"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("screen", String.valueOf(screen)));
            nvps.add(new BasicNameValuePair("filterField", filterField));
            nvps.add(new BasicNameValuePair("filterValue", filterValue));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            //System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            //System.out.println("----------------------------------------");
            //System.out.println(responseBody);//Server response
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ContainersManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error listAllUsersFilter";
    }
    
    /**
     * Mètode per obtenir un llistat de contenidors amb filtres
     * 
     * @param url URL del servidor
     * @param token Identificatiu de sessió iniciada
     * @param screen Número de pàgina sol·licitada al servidor. 0 és la primera
     * @param type Tipus de contenidor o tots els tipus
     * @param operative Valor 1 per mostrat els operatius, 0 per veure els no operatius o tots
     * @return La resposta del servidor
     */
    public String listFilteredContainers(String url, String token, int screen, String type, int operative) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ListFilteredContainers"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("screen", String.valueOf(screen)));
            nvps.add(new BasicNameValuePair("type", type));
            nvps.add(new BasicNameValuePair("operative", String.valueOf(operative)));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);//Server response
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ContainersManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error listfilteredContainers";
    }
    
    /**
     * Mètode per esborrar un contenidor
     * 
     * @param url URL del servidor
     * @param token Identificatiu de sessió iniciada
     * @param containerId Id del contenidor a esborrar
     * @return 
     */
    public String containerDelete(String url, String token, String containerId) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ContainerDelete"));
            nvps.add(new BasicNameValuePair("token", token));
            nvps.add(new BasicNameValuePair("containerId", containerId));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody); //Server response
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ContainersManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error containerDelete";
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
