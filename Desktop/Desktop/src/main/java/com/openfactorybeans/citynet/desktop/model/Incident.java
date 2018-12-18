package com.openfactorybeans.citynet.desktop.model;

import java.util.Date;

/**
 * Model de la classe Incident
 * @author Jose
 */
public class Incident {
    
    //Variables de tipus de incidència
    public static final String BROKEN = "broken";
    public static final String FULL = "full";
    public static final String BROKEN_TXT = "Trencat";
    public static final String FULL_TXT = "Ple";
    
    private int id;
    private String container, userEmail, type, date, resolutionDate;

    public Incident() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(String resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

}
