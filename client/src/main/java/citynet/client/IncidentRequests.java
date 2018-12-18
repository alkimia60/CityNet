/*
 * @author Francisco Javier Diaz Garzon
 * Class to make http incident requests to the server
 */
package citynet.client;

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
import citynet.client.model.Incident;

public class IncidentRequests {

    private static final String LOCAL_URL = "http://localhost:8084/citynet/IncidentManager";
    private static final String PUBLIC_URL = "http://ec2-35-180-7-53.eu-west-3.compute.amazonaws.com:8080/citynet/IncidentManager";
    private static final String URL = PUBLIC_URL;
    private static String sessionToken; //Session token

    public final static void main(String[] args) {
        //User login and session token
        ClientRequests cliRqsts = new ClientRequests();
        cliRqsts.userLogin("http://ec2-35-180-7-53.eu-west-3.compute.amazonaws.com:8080/citynet/UserManager", "diazgx@diba.cat", "X4vix@vi");
        sessionToken = ClientRequests.token;

        //Incident to notify
        Incident incident = new Incident("BBB666", Incident.IT_FULL);
        IncidentRequests incRqsts = new IncidentRequests();
        //Incident notification
        //incRqsts.incidentNotification(URL, sessionToken, incident);
        //Incident finalization
        //incRqsts.incidentFinalize(URL, sessionToken,12);
        //List incidents filtered by type and resolution_date state
        incRqsts.listFilteredIncidents(URL, sessionToken, 0, "", -1);
        

    }

    /**
     * Function to request the registration of an incident
     *
     * @param url Servlet location
     * @param sessionToken
     * @param incident Incident object to register
     * @return json String with elements "ok" or "error"
     */
    private String incidentNotification(String url, String sessionToken, Incident incident) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            //Gson object to convert Container Object into String
            Gson gson = new Gson();
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "IncidentNotification"));
            nvps.add(new BasicNameValuePair("token", sessionToken));
            nvps.add(new BasicNameValuePair("incident", gson.toJson(incident)));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody; //Server response

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error incidentNotification";
    }

    /**
     * Function to request the finalization of an incident
     *
     * @param url Servlet location
     * @param sessionToken
     * @param incident Incident object to register
     * @return json String with elements "ok" or "error"
     */
    private String incidentFinalize(String url, String sessionToken, int incidentID) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            //Gson object to convert Container Object into String
            Gson gson = new Gson();
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "IncidentFinalize"));
            nvps.add(new BasicNameValuePair("token", sessionToken));
            nvps.add(new BasicNameValuePair("incidentId", String.valueOf(incidentID)));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody; //Server response

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error incidentFinalize";
    }
    
    /**
     * Function to list incidents by type and resolution state
     * @param url Servlet location
     * @param sessionToken
     * @param screen current application screen number, starting with 0
     * @param type sting of type of container or all types
     * @param finalized int 1 if container is operative or 0 if not or any for all
     * @return json String with elements startOfTable, endOfTable and the
     * filtered incident objects
     */
    private String listFilteredIncidents (String url, String sessionToken, int screen, String type, int finalized) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            //Gson object to convert Container Object into String
            Gson gson = new Gson();
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ListFilteredIncidents"));
            nvps.add(new BasicNameValuePair("token", sessionToken));
            nvps.add(new BasicNameValuePair("screen", String.valueOf(screen)));
            nvps.add(new BasicNameValuePair("type", type));
            nvps.add(new BasicNameValuePair("finalized", String.valueOf(finalized)));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody; //Server response

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error listFilteredIncidents";
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
