
package com.openfactorybeans.citynet.desktop.utils;

import com.openfactorybeans.citynet.desktop.model.Container;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Classe que descarrega una imatge de google maps amb la posició del contenidor
 *
 * @author Jose
 */
public class ContainerMap {
    
    //Variables de la classe
    private String imageURL;
    private Image image;
    
    //Variables finals
    private final String ZOOM = "18";
    private final String SIZE = "500x275";
    private final String MAP_TYPE = "roadmap";
    private final String API_KEY = "AIzaSyDsA2t8Igjq6fR3iPAN70Ug3rW-UaRqNeI";
    
    //Variables finals de tipus de contenidor
    private final String PAPER = "Paper i cartró";
    private final String GLASS = "Vidre";
    private final String PACKAGING = "Envasos lleugers";
    private final String ORGANIC = "Matèria orgànica";
    private final String TRASH = "Rebuig";
    
    //Variables del contenidor per definir el mapa
    String latitude, longitude, type, color, label;

    /**
     * Constructor que rep un contenidor per construïr una URL i descarregar
     * un mapa estàtic de google maps
     * 
     * @param container Contenidor per mostrar al mapa
     */
    public ContainerMap(Container container) {
        
        //Obtenim les dades necessaries del contenidor
        latitude = container.getLatitude().toString();
        longitude = container.getLongitude().toString();
        
        //La marca en el mapa es posarà segons el tipus de contenidor (color i etiqueta)
        type = container.getType();
        
        if (type.equals(PAPER)) {
            color = "blue";
            label = "P";
        }
        
        if (type.equals(GLASS)) {
            color = "green";
            label = "V";
        }
        
        if (type.equals(PACKAGING)) {
            color = "yellow";
            label = "E";
        }
        
        if (type.equals(ORGANIC)) {
            color = "brown";
            label = "O";
        }
        
        if (type.equals(TRASH)) {
            color = "gray";
            label = "R";
        }
        
        //Construïm la URL per descarregar la imatge de google maps
        imageURL = "https://maps.googleapis.com/maps/api/staticmap" +
                "?center=" + latitude + "," + longitude +
                "&zoom=" + ZOOM +
                "&size=" + SIZE +
                "&maptype=" + MAP_TYPE +
                "&markers=color:" + color + "|label:" + label + "|" + latitude + "," + longitude +
                "&key=" + API_KEY;
        
    }
    
    /**
     * Mètode que ens retorna una imatge de google maps amb els paràmetres que li passem en la URL
     * 
     * @return La imatge estàtica de google maps amb els paraàmetres que s'ha especificat
     */
    public Image downloadMap() {
        
        //Descarreguem el mapa
        try {

            URL url = new URL(imageURL);
            image = ImageIO.read(url);
            return image;

        } catch (MalformedURLException ex) {
            Logger.getLogger(ContainerMap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContainerMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return image;
        
    }
    
}
