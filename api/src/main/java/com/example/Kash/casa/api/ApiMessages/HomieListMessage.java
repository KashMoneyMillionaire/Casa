package com.example.Kash.casa.api.ApiMessages;

import com.example.Kash.casa.api.Domain.UserProfile;

import java.util.List;

/**
 * Created by Kash on 2/15/2015.
 * Do work homie.
 */
public class HomieListMessage
{
    private List<UserProfile> UserProfiles;

    public HomieListMessage(List<UserProfile> ups){
        UserProfiles = ups;
    }

    public List<UserProfile> getUserProfiles()
    {
        return UserProfiles;
    }

    public void setUserProfiles(List<UserProfile> userProfiles)
    {
        UserProfiles = userProfiles;
    }
}
