/**
 *
 * @author Francisco Javier Diaz Garzon
 */
package citynet.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextUtils {

    public static String jsonErrorMessage(String message) {
        //return "{\"Status\":{\"error\":\"" + message + "\"}}";
        return "{\"error\":\"" + message + "\"}";
    }

    public static String jsonOkMessage(String message) {
        //return "{\"Status\":{\"OK\":\"" + message + "\"}}";
        return "{\"OK\":\"" + message + "\"}";
    }

    public static String jsonTokenRolMessage(String token, String rol) {
        return "{\"token\":\"" + token + "\",\"rol\":\"" + rol + "\"}";
    }

    public static String jsonUserDate(String user, String date) {
        return "{\"user\":\"" + user + "\",\"date\":\"" + date + "\"}";
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isJsonEmptyValues(String jsonData) {
        if (jsonData == null) {
            return true;
        }
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //read JSON like DOM Parser
            JsonNode rootNode = objectMapper.readTree(jsonData);
            Iterator<JsonNode> elements = rootNode.elements();
            while (elements.hasNext()) {
                JsonNode key = elements.next();
                if (key.textValue().trim().isEmpty()) {
                    return true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TextUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    
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
            Logger.getLogger(TextUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
    
}
