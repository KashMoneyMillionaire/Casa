package com.example.Kash.casa.api.Domain;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Subclass;


/**
 * Created by Kash on 1/13/2015.
 */

@Subclass(name = "Device")
public class Device extends CasaEntity {

    private String Name;
    private String AndroidId;
    private int Height;
    private int Width;

    private Key<UserProfile> User;

    public Device(String id, String name, int height, int width){
        Name = name;
        Height = height;
        Width = width;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public int getHeight() {
        return Height;
    }
    public void setHeight(int height) {
        Height = height;
    }

    public int getWidth() {
        return Width;
    }
    public void setWidth(int width) {
        Width = width;
    }

    public Key<UserProfile> getUser() {
        return User;
    }
    public void setUser(Key<UserProfile> user) {
        User = user;
    }

    public String getAndroidId() {
        return AndroidId;
    }
    public void setAndroidId(String androidId) {
        AndroidId = androidId;
    }
}

