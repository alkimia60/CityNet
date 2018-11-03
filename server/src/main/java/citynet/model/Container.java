/**
 *
 * @author Francisco Javier Diaz Garzon
 * Container model class
 */
package citynet.model;

public class Container {

    //Constants for container types
    public static final String[] CONTAINER_TYPES = {"paper", "glass", "packaging", "organic", "trash"};
    private String id, type;
    private double latitude, longitude;
    private boolean operative = true;
    private int active_incident;

    public Container() {
    }

    public Container(String id, String type, double latitude, double longitude) {
        this.id = id;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getActive_incident() {
        return active_incident;
    }

    public void setActive_incident(int active_incident) {
        this.active_incident = active_incident;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOperative() {
        return operative;
    }

    public void setOperative(boolean operative) {
        this.operative = operative;
    }

}
