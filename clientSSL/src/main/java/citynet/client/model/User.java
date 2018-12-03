/**
 *
 * @author Francisco Javier Diaz Garzon
 * User model class
 */
package citynet.client.model;

/**
 *
 * User model class
 */
public class User {

    //Constants for user_level role
    public static final String UL_USER = "user";
    public static final String UL_EDITOR = "editor";
    public static final String UL_ADMIN = "admin";

    private String email, password, name, surname, address, postcode, city, userLevel;

    /**
     * User object constructor
     *
     * @param email
     * @param password
     * @param name
     * @param surname
     * @param address
     * @param postcode
     * @param city
     */
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

    /**
     *
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return user name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return user surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param surname user surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @return user address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address user address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return user postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     *
     * @param postcode user postcode
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     *
     * @return user city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city user city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return user role
     */
    public String getUserLevel() {
        return userLevel;
    }

    /**
     *
     * @param userLevel user role
     */
    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

}
