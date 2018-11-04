/*
 * @author Francisco Javier Diaz Garzon
 * Class to make http container requests to the server
 */
package citynet.client;

import citynet.client.model.Container;
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

public class ContainerRequests {

    private static final String LOCAL_URL = "http://localhost:8084/citynet/ContainerManager";
    private static final String PUBLIC_URL = "http://ec2-35-180-7-53.eu-west-3.compute.amazonaws.com:8080/citynet/ContainerManager";
    private static final String URL = PUBLIC_URL;
    private static String sessionToken; //Session token

    public final static void main(String[] args) {
        //User login and session token
        ClientRequests cliRqsts = new ClientRequests();
        cliRqsts.userLogin("http://ec2-35-180-7-53.eu-west-3.compute.amazonaws.com:8080/citynet/UserManager", "diazgx@diba.cat", "xavixavi");
        sessionToken = ClientRequests.token;

        //Container to register
        Container container = new Container("CCC999", Container.CONTAINER_TYPES[4], 41.454545, 2.4545454);
        ContainerRequests contRqsts = new ContainerRequests();
        //Register container
        contRqsts.containerRegister(URL, sessionToken, container);
        //List all Containers with filter
        contRqsts.listAllContainers(URL, sessionToken, 0, "type", "trash");
        //Find Container Incident by container id
        contRqsts.containerIncident(URL, sessionToken, container.getId());
        
    }

 
    /**
     * Function to request the registration of a container
     * @param url Servlet location
     * @param sessionToken 
     * @param container Container object to register
     * @return json String with elements "ok" or "error"
     */
    private String containerRegister(String url, String sessionToken, Container container) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            //Gson object to convert Container Object into String
            Gson gson = new Gson();
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ContainerRegister"));
            nvps.add(new BasicNameValuePair("token", sessionToken));
            nvps.add(new BasicNameValuePair("container", gson.toJson(container)));

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
        return "Error containerRegister";
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

    /**
     * Function to request 10 rows of all containers ordered by id
     *
     * @param url servlet location
     * @param sessionToken session token
     * @param screen current application screen number, starting with 0
     * @param filterField field that want to be filtered. If it is not valid, it shows everything
     * @param filterValue value of the field for which it is to be filtered. If it is not valid, it shows everything
     * @return json String with elements startOfTable, endOfTable and the
     * filtered containers objects
     */
    private String listAllContainers(String url, String sessionToken, int screen, String filterField, String filterValue) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ListAllContainers"));
            nvps.add(new BasicNameValuePair("token", sessionToken));
            nvps.add(new BasicNameValuePair("screen", String.valueOf(screen)));
            nvps.add(new BasicNameValuePair("filterField", filterField));
            nvps.add(new BasicNameValuePair("filterValue", filterValue));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            String responseBody = httpclient.execute(httpPost, customResponseHandler());
            System.out.println("----------------------------------------");
            System.out.println(responseBody);//Server response
            return responseBody;

        } catch (Exception ex) {
            Logger.getLogger(ClientRequests.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "Error listAllContainers";
    }

       /**
     * Function to request the incident of a container
     *
     * @param url Servlet location
     * @param containerId id of the container to find incident
     * @return json String with incidnet or "error"
     */
    private String containerIncident(String url, String sessionToken, String containerId) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            //Gson object to convert Container Object into String
            Gson gson = new Gson();
            //List of paràmeters to send
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "ContainerIncident"));
            nvps.add(new BasicNameValuePair("token", sessionToken));
            nvps.add(new BasicNameValuePair("container", gson.toJson(containerId)));

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
        return "Error containerIncident";
    }
}
