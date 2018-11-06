/**
 *
 * @author Francisco Javier Diaz Garzon
 * Incident model class
 */
package citynet.client.model;

import java.util.Date;

public class Incident {
    //Constants for incident types
    public static final String IT_FULL = "full";
    public static final String IT_BROKEN = "broken";
    private int id;
    private Date date;
    private String container,user_email,type;
    private Date resolution_date;

    public Incident() {
    }

    public Incident(String container, String type) {
        this.container = container;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getResolution_date() {
        return resolution_date;
    }

    public void setResolution_date(Date resolution_date) {
        this.resolution_date = resolution_date;
    }

}
