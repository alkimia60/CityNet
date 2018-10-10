/*
 * @author Francisco Javier Diaz Garzon
 * Class to make http requests to the server
 */
package citynet.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

 class ClientRequests {
    private static final String LOCAL_URL ="http://localhost:8084/citynet/UserManager";
    private static final String PUBLIC_URL ="http://ec2-35-180-7-53.eu-west-3.compute.amazonaws.com:8080/citynet/UserManager";

    public final static void main(String[] args) {
        ClientRequests cr = new ClientRequests();
        cr.ListAllUsers(2,PUBLIC_URL);
    }

    /**
     * Function to request the list of all users alphabetically ordered
     * @param screen current screen number, starting with 0 
     * @return String with startOfTable, endOfTable and the fields of the 
     * records between #. All records separated by \n
     */
    private String ListAllUsers(int screen, String url) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("screen", String.valueOf(screen)));
            //nvps.add(new BasicNameValuePair("screen", "prova"));
            nvps.add(new BasicNameValuePair("action", "ListAllUsers"));

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
            Logger.getLogger(ClientRequests.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error ListAllUsers";
    }

}
