package com.example.Kash.casa.api.Model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Subclass;


/**
 * Created by Kash on 1/13/2015.
 */

@Subclass(name = "Device")
public class Device extends CasaEntity {

    private String Name;
    private int Height;
    private int Width;

    private Key<UserProfile> User;

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
}

