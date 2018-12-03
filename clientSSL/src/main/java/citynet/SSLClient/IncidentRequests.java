/*
 * @author Francisco Javier Diaz Garzon
 * Class to make http incident requests to the server
 */
package citynet.SSLClient;

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
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContextBuilder;

public class IncidentRequests {

    private static final String LOCAL_SSL_URL = "https://localhost:8443";
    private static final String LOCAL_URL = "http://localhost:8084";
    private static final String PUBLIC_SSL_URL = "https://ec2-35-180-7-53.eu-west-3.compute.amazonaws.com:8443";
    private static final String PUBLIC_URL = "http://ec2-35-180-7-53.eu-west-3.compute.amazonaws.com:8080";
    private static final String URL = LOCAL_URL;
    private static final String URI = URL + "/citynet/IncidentManager";
    private static String sessionToken; //Session token

    public final static void main(String[] args) {
        //Initialize httpClient and httpPost
        try {
            SSLContext sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, (certificate, authType) -> true).build();
            CloseableHttpClient httpclient = HttpClients.custom().setSSLContext(sslContext)
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
                    //.setRedirectStrategy(new LaxRedirectStrategy()) //Redirecció automàtica
                    .build();
            HttpPost httpPost = new HttpPost(URL + "/citynet/UserManager"); //UserManager URI

            //User login for session token
            ClientRequests cliRqsts = new ClientRequests();
            cliRqsts.userLogin(httpclient, httpPost, "diazgx@diba.cat", "xavixavi");
            sessionToken = ClientRequests.token;

            httpPost.setURI(new URI(URI));//ContainerManager URI
            //Incident to notify
            Incident incident = new Incident("23ESDE", Incident.IT_FULL);
            IncidentRequests incRqsts = new IncidentRequests();
            //Incident notification
            incRqsts.incidentNotification(httpclient, httpPost, sessionToken, incident);
            //Incident finalization
            //incRqsts.incidentFinalize(httpclient, httpPost, sessionToken,12);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ContainerRequests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(ContainerRequests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(ContainerRequests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ContainerRequests.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Function to request the registration of an incident
     *
     * @param httpclient
     * @param httpPost
     * @param sessionToken
     * @param incident Incident object to register
     * @return json String with elements "ok" or "error"
     */
    private String incidentNotification(CloseableHttpClient httpclient, HttpPost httpPost, String sessionToken, Incident incident) {
        try {
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
            //Checks if response is a redirection and then re-execute request            
            responseBody = reExecuteIfRedirected(responseBody, httpPost, httpclient);
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
     * @param httpclient
     * @param httpPost
     * @param sessionToken
     * @param incident Incident object to register
     * @return json String with elements "ok" or "error"
     */
    private String incidentFinalize(CloseableHttpClient httpclient, HttpPost httpPost, String sessionToken, int incidentID) {
        try {
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
            //Checks if response is a redirection and then re-execute request            
            responseBody = reExecuteIfRedirected(responseBody, httpPost, httpclient);
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
