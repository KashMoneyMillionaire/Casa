package com.example.kash.casa.Helpers;

import android.content.Context;
import android.content.Intent;

import com.example.kash.casa.Activities.MainActivity;
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

        //Continue on
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }
}
