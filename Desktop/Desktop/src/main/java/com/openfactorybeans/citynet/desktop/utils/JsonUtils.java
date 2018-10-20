/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfactorybeans.citynet.desktop.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openfactorybeans.citynet.desktop.users.User;
import com.sun.istack.internal.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

/**
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
        
        String value = "No json data";
        
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
     * Passa a un ArrayList el contingut del json. converteix cada element en un objecte user
     * @param jsonData Cadena Json
     * @return El ArrayList amb els users
     */
    public static List<User> parseJsonUser (String jsonData) {
        
        List<User> users;
        users = new ArrayList<>();
        
        //Creació d'intància ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        
        //Lectura Json
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
     * Passa a un ArrayList el contingut del json. converteix cada element en un objecte user
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
