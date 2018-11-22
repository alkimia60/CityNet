package com.openfactorybeans.citynet.desktop.model;

/**
 * Model de la classe Container
 * @author Jose
 */
public class Container {
    
    //Declaració de variables de tipus de contenidors per passar al servidor
    public static final String TYPES_SERVER[] = {
        "paper",
        "glass",
        "packaging",
        "organic",
        "trash"
    };
    
    //Declaració de variables de tipus de contenidors per mostrar a la taula
    public static final String TYPES_TABLE[] = {
        "Paper i cartró",
        "Vidre",
        "Envasos lleugers",
        "Matèria orgànica",
        "Rebuig"
    };
    
    //Declaració de variables de contenidors operatius per passar al servidor
    public static final String OPERATIVE_SERVER[] = {
        "0", //És valor si o true
        "1", //És valor no o false
    };
    
    //Declaració de variables de contenidors operatius per mostrar a la taula
    public static final String OPERATIVE_TABLE[] = {
        "No",
        "Si",
    };
    
    //Declaració de variables de la classe
    private String id, type;
    private Double latitude, longitude;
    private Boolean operative;
    private int activeIncident;

    //Default constructor
    public Container() {
    }

    public Container(String id, String type, Double latitude, Double longitude, Boolean operative, int activeIncident) {
        this.id = id;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.operative = operative;
        this.activeIncident = activeIncident;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getOperative() {
        return operative;
    }

    public void setOperative(Boolean operative) {
        this.operative = operative;
    }

    public int getActiveIncident() {
        return activeIncident;
    }

    public void setActiveIncident(int activeIncident) {
        this.activeIncident = activeIncident;
    }
    
}
