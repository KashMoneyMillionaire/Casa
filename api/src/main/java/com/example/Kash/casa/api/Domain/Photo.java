package com.example.Kash.casa.api.Domain;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Subclass;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kash on 1/13/2015.
 */

@Subclass(name = "Photo")
public class Photo extends CasaEntity {

    private byte[] Image;
    private Date DateSent;

    private List<Key<Device>> DevicesTo = new ArrayList<>();

    private Key<UserProfile> UserFrom;

    public byte[] getImage() {
        return Image;
    }
    public void setImage(byte[] image) {
        Image = image;
    }

    public Date getDateSent() {
        return DateSent;
    }
    public void setDateSent(Date dateSent) {
        DateSent = dateSent;
    }

    public List<Key<Device>> getDevicesTo() {
        return DevicesTo;
    }

    public Key<UserProfile> getUserFrom() {
        return UserFrom;
    }
}
