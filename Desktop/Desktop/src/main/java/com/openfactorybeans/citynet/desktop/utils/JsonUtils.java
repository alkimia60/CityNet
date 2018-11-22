package com.openfactorybeans.citynet.desktop.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openfactorybeans.citynet.desktop.model.Container;
import com.openfactorybeans.citynet.desktop.model.Incident;
import com.openfactorybeans.citynet.desktop.model.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

/**
 * Codificació d'utilitats json
 * 
 * @author Jose
 */
public class JsonUtils {
    
    /**
     * Busca el valor d'un element json
     * @param jsonData Cadena Json
     * @param key Element a buscar
     * @return Valor trobat o missatge d'informació si no l'ha trobat
     */
    public static String findJsonValue(String jsonData, String key) {
        
        String value = null;
        
        if (jsonData == null) {

            return value;
            
        }
        
        //Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
            
            //Read Json like DOM Parser
            JsonNode rootNode = objectMapper.readTree(jsonData);
            
            if (rootNode.findValue(key) != null) {
                
                //Find key in all json
                JsonNode keyNode = rootNode.findValue(key);
                
                value = keyNode.asText();

                return value;
                
            } else {

                return value;

            }

        } catch (IOException ex) {
            
            java.util.logging.Logger.getLogger(JsonUtils.class.getName()).log(Level.SEVERE, null, ex);
            
        }

        return value;
        
    }
    
    /**
     * Passa a un ArrayList el contingut del json. Converteix cada element en un objecte User
     * @param jsonData Cadena json
     * @return El ArrayList amb els users
     */
    public static List<User> parseJsonUsers (String jsonData) {
        
        List<User> users;
        users = new ArrayList<>();
        
        //Creació intància ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        
        //Lectura json
        JsonNode rootNode, usersNode;
        
        try {
            
            rootNode = objectMapper.readTree(jsonData); //Definim el node arrel
            usersNode = rootNode.path("users"); //Asignem la ruta de "users"
            
            //Recorrem els elements de JSonNode "users"
            Iterator<JsonNode> elements = usersNode.elements();
            while (elements.hasNext()) {
                
                JsonNode next = elements.next();
                
                User userForAdd = new User();
                
                userForAdd.setEmail(next.findValue("email").asText());
                userForAdd.setName(next.findValue("name").asText());
                userForAdd.setSurname(next.findValue("surname").asText());
                userForAdd.setAddress(next.findValue("address").asText());
                userForAdd.setPostcode(next.findValue("postcode").asText());
                userForAdd.setCity(next.findValue("city").asText());
                userForAdd.setUserLevel(next.findValue("userLevel").asText());

                users.add(userForAdd);
                
            }
            
        } catch (IOException ex) {
            
            java.util.logging.Logger.getLogger(JsonUtils.class.getName()).log(Level.SEVERE, null, ex);
            
        }
     
        return users;
        
    }
    
    /**
     * Passa a un objecte de la classe User el contingut del json.
     * @param jsonData Cadena json
     * @return L'objecte User
     */
    public static User parseJsonUser (String jsonData) {
        
        User user = new User();
        
        //Creació instància ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        
        //Lectura json
        JsonNode rootNode;
        
        try {
            
            rootNode = objectMapper.readTree(jsonData); //Definim el node arrel
            
            //Passem les dades
            user.setEmail(rootNode.findValue("email").asText());
            user.setName(rootNode.findValue("name").asText());
            user.setSurname(rootNode.findValue("surname").asText());
            user.setAddress(rootNode.findValue("address").asText());
            user.setPostcode(rootNode.findValue("postcode").asText());
            user.setCity(rootNode.findValue("city").asText());
            user.setPassword(null);
            
        } catch (IOException ex) {
            
            java.util.logging.Logger.getLogger(JsonUtils.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return user;
        
    }
    
    /**
     * Passa a un ArrayList el contingut del json. Converteix cada element en un objecte Container
     * @param jsonData Cadena json
     * @return El ArrayList amb els contenidors
     */
    public static List<Container> parseJsonContainers (String jsonData) {
        
        List<Container> containers;
        containers = new ArrayList<>();
        
        //Creació instància ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        
        //Lectura json
        JsonNode rootNode, containerNode;
        
        try {
            
            rootNode = objectMapper.readTree(jsonData); //Definim el node arrel
            containerNode = rootNode.path("containers"); //Asignem la ruta de "containers"
            
            //Recorrem els elements de JSonNode "containers"
            Iterator<JsonNode> elements = containerNode.elements();
            while (elements.hasNext()) {
                
                JsonNode next = elements.next();
                
                Container containerForAdd = new Container();
                
                containerForAdd.setId(next.findValue("id").asText());
                containerForAdd.setType(next.findValue("type").asText());
                containerForAdd.setLatitude(next.findValue("latitude").asDouble());
                containerForAdd.setLongitude(next.findValue("longitude").asDouble());
                containerForAdd.setOperative(next.findValue("operative").asBoolean());
                containerForAdd.setActiveIncident(next.findValue("active_incident").asInt());
                
                containers.add(containerForAdd);

            }
            
        } catch (IOException ex) {
            
            java.util.logging.Logger.getLogger(JsonUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return containers;
        
    }
    
    /**
     * Passa a un objecte de la classe Incident el contingut del json.
     * @param jsonData Cadena json
     * @return L'objecte Incident
     */
    public static Incident parseJsonIncident (String jsonData) {
        
        Incident incident = new Incident();
        
        //Creació instància ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        
        //Lectura json
        JsonNode rootNode;
        
        try {
            
            rootNode = objectMapper.readTree(jsonData); //Definim el node arrel
            
            //Passem les dades
            incident.setId(rootNode.findValue("id").asInt());
            incident.setDate(rootNode.findValue("date").asText());
            incident.setContainer(rootNode.findValue("container").asText());
            incident.setUserEmail(rootNode.findValue("user_email").asText());
            incident.setType(rootNode.findValue("type").asText());

        } catch (IOException ex) {
            
            java.util.logging.Logger.getLogger(JsonUtils.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return incident;
        
    }
    
    /**
     * Mirem si el json retorna final de llistat
     * @param jsonData Cadena Json
     * @return true si és final de llistat o false si no és final de llistat
     */
    public static boolean parseJsonControl (String jsonData) {
        
        //Variable de retorn
        boolean end = false;
        
        //Creació d'intància ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        
        //Lectura Json
        JsonNode rootNode, controlNode;
        
        try {
            
            rootNode = objectMapper.readTree(jsonData); //Definim el node arrel
            
            controlNode = rootNode.path("control"); //}Asignem la ruta de "control"
            JsonNode endNode = rootNode.findValue("end"); //Asignem el valor de l'element "end"
            
            if (endNode.asText().equals("true")) {
                
                //És final del llistat
                end = true;
                
            }
            
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(JsonUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return end;
     
    }
    
}
