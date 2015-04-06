package com.example.Kash.casa.api.Domain;

import com.example.Kash.casa.api.Helpers.Deref;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Subclass;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kash on 1/13/2015.
 */

@Subclass(name = "Photo")
public class Photo extends CasaEntity
{
    private byte[] Image;
    private Date DateSent;

    private List<Ref<Device>> DevicesTo = new ArrayList<>();
    @Parent
    Ref<UserProfile> UserFrom;

    public byte[] getImage()
    {
        return Image;
    }

    public void setImage(byte[] image)
    {
        Image = image;
    }

    public Date getDateSent()
    {
        return DateSent;
    }

    public void setDateSent(Date dateSent)
    {
        DateSent = dateSent;
    }

    public List<Device> getDevicesTo()
    {
        return Deref.deRef(DevicesTo);
    }

    public void setUserFrom(UserProfile user)
    {
        UserFrom = Ref.create(user);
    }

    public UserProfile getUserFrom()
    {
        return UserFrom.get();
    }
}
