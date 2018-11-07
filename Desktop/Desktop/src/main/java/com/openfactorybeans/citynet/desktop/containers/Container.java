package com.openfactorybeans.citynet.desktop.containers;

/**
 * Model de la classe contenidor
 * @author Jose
 */
public class Container {
    
    //Declaració de variables de tipus de contenidors
    public static final String PAPER = "paper";
    public static final String GLASS = "glass";
    public static final String PACKAGING = "packaging";
    public static final String ORGANIC = "organic";
    public static final String TRASH = "trash";
    
    //Declaració de variables de la classe
    private String id, type;
    private Double longitude, latitude;

    //Default constructor
    public Container() {
    }

    public Container(String id, String type, Double longitude, Double latitude) {
        this.id = id;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    

}
