package com.example.kash.casa.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.kash.casa.api.userProfileApi.model.UserProfile;

/**
 * Created by Kash on 2/13/2015.
 */
public class CasaPreferenceManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    public enum PrefType {
        Login(1);

        private final int value;

        private PrefType(final int newValue) {
            value = newValue;
        }

        public int getValue() { return value; }
    }

    private static final String LOGIN_INFO_PREF_NAME = "CasaPref";
    private static final String PREF_TYPE = "CasaPref";

    private static final String IS_LOGIN = "isLoggedIn";
    private static final String USERNAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_LOGIN_DATE = "loginDate";

    public CasaPreferenceManager(Context context, PrefType prefType){
        this._context = context;
        pref = _context.getSharedPreferences(LOGIN_INFO_PREF_NAME + prefType, PRIVATE_MODE);
        editor = pref.edit();
        editor.putInt(PREF_TYPE, prefType.getValue());
        editor.apply();
    }

    public void logInUser(UserProfile currentUser){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USERNAME, currentUser.getUsername());
        editor.putString(USER_EMAIL, currentUser.getEmail());
        editor.putString(USER_LOGIN_DATE, currentUser.getLastLogInDate().toString());
        editor.commit();
    }

    public boolean logoutUser(){
        if (pref.getInt(PREF_TYPE, 0) != PrefType.Login.getValue()) return false;

        editor.clear();
        editor.commit();
        return true;
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
