/**
 *
 * @author Francisco Javier Diaz Garzon
 * Class user model
 */
package citynet.client.model;

public class User {

    public static final String UL_USER = "user";
    public static final String UL_EDITOR = "editor";
    public static final String UL_ADMIN = "admin";

    private String email, password, name, surname, address, postcode, city, userLevel;

    public User(String email, String password, String name, String surname, String address, String postcode, String city) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.userLevel = UL_USER;
    }

    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

}
