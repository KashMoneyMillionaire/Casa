package com.example.kash.casa.Tasks;

import android.os.AsyncTask;

import com.example.kash.casa.Helpers.UserProfileApiFactory;
import com.example.kash.casa.Models.DeviceModel;
import com.example.kash.casa.Models.UserProfileModel;
import com.example.kash.casa.api.userProfileApi.UserProfileApi;
import com.example.kash.casa.api.userProfileApi.model.UserProfile;
import com.example.kash.casa.api.userProfileApi.model.UserProfileMessage;

import java.io.IOException;


/**
 * Created by Kash on 1/19/2015.
 */
public abstract class UserRegisterTask extends AsyncTask<UserProfileModel, Void, UserProfile>
{
    private static UserProfileApi userProfileService = null;

    protected abstract void onPostExecute(UserProfile success);

    protected abstract void onCancelled();

    @Override
    protected UserProfile doInBackground(UserProfileModel... params)
    {
        if (params.length == 0 || params[0] == null)
            return null;

        if (userProfileService == null)
        {
            userProfileService = UserProfileApiFactory.getApi();
        }

        UserProfileModel userProfile = params[0];
        UserProfileMessage message;

        try
        {
            DeviceModel device = userProfile.Device;
            message = userProfileService.register(userProfile.username, userProfile.email,
                                                  userProfile.password,
                                                  device.name, device.height, device.width,
                                                  device.id)
                                        .execute();
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        //determine outcome of message, return proper stuff
        UserProfile returnProfile = new UserProfile();
        if (message == null)
        {
            returnProfile.setUsername("$error");
        }
        else if (message.getIsSuccess())
        {
            returnProfile = message.getSuccessEntity();
        }
        else if (message.getMessage().equals("Username exists"))
        {
            returnProfile.setUsername("$username");
        }
        else if (message.getMessage().equals("Email exists"))
        {
            returnProfile.setUsername("$email");
        }
        return returnProfile;
    }


}