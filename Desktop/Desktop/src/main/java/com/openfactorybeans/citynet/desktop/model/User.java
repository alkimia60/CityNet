package com.openfactorybeans.citynet.desktop.model;

/**
 * Model de la classe User
 * @author Jose
 */
public class User {
    
    //Variables de userLevel
    public static final String UL_USER = "user";
    public static final String UL_EDITOR = "editor";
    private static final String UL_ADMIN = "admin";
    
    //Declaració de variables de la classe
    private String email, password, name, surname, address, postcode, city, userLevel;
    
    //Default constructor
    public User() {
    }
    

    public User(String email, String name, String surname, String address, String postcode, String city, String password) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.password = password;
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
