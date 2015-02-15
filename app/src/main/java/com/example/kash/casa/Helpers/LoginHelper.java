package com.example.kash.casa.Helpers;

import android.content.Context;

import com.example.kash.casa.api.userProfileApi.model.UserProfile;

/**
 * Created by Kash on 2/14/2015.
 */
public final class LoginHelper {

    private LoginHelper() {}

    public static void setLoginPreferencesAndContinue(UserProfile userProfile, Context context){
        //set login information
        CasaPreferenceManager preferenceManager = new CasaPreferenceManager(context, CasaPreferenceManager.PrefType.Login);
        preferenceManager.logInUser(userProfile);

        //TODO continue on to home page

    }
}
