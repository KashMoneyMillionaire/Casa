package com.example.kash.casa.Models;

/**
 * Created by Kash on 1/19/2015.
 */
public class UserProfileModel {
    public String username;
    public String email;
    public String password;
    public DeviceModel Device;

    public UserProfileModel(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
