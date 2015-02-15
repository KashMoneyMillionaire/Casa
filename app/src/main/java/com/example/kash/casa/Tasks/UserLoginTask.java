package com.example.kash.casa.Tasks;

import android.os.AsyncTask;

import com.example.kash.casa.Models.UserProfileModel;
import com.example.kash.casa.R;
import com.example.kash.casa.api.userProfileApi.model.UserProfile;

/**
 * Created by Kash on 2/14/2015.
 */
public abstract class UserLoginTask extends AsyncTask<UserProfileModel, Void, UserProfile> {

    protected abstract void onPostExecute(UserProfile success);
    protected abstract void onCancelled();

    @Override
    protected UserProfile doInBackground(UserProfileModel... params) {
        // TODO: attempt authentication against a network service.


        // TODO: register the new account here.
        return null;
    }
}
