/**
 *
 * @author Francisco Javier Diaz Garzon
 */
package citynet.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Class of utilities for json Objects
 */
public class JsonUtils {

    /**
     * Find of a json key
     * @param jsonData
     * @param key
     * @return value of the json key
     */
    public static String findJsonValue(String jsonData, String key) {
        String value = "No json data";
        if (jsonData == null) {
            return value;
        }
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //read JSON like DOM Parser
            JsonNode rootNode = objectMapper.readTree(jsonData);
            if (rootNode.findValue(key) != null) {
                JsonNode keyNode = rootNode.findValue(key); //find key in all json
                value = keyNode.asText();
                return value;
            }else{
                return value;
            }
        } catch (IOException ex) {
            Logger.getLogger(JsonUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }

}
