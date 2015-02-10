package com.example.kash.casa.Services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.example.kash.casa.Models.UserProfileModel;
import com.example.kash.casa.api.userProfileApi.UserProfileApi;
import com.example.kash.casa.api.userProfileApi.model.UserProfile;
import com.example.kash.casa.api.userProfileApi.model.UserProfileMessage;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;

import java.io.IOException;


/**
 * Created by Kash on 1/19/2015.
 */
public class UserRegisterTask extends AsyncTask<Pair<Context, UserProfileModel>, Void, Boolean> {

    private static UserProfileApi userProfileService = null;
    private Context context;

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Toast.makeText(context, "Registration complete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Registration failure", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected Boolean doInBackground(Pair<Context, UserProfileModel>... params) {

        if (params.length == 0 || params[0] == null || params[0].first == null || params[0].second == null)
            return false;

        if (userProfileService == null) {  // Only do this once
            UserProfileApi.Builder builder = new UserProfileApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),
                    //TODO remove this when not debugging
                    new HttpRequestInitializer() {
                        public void initialize(HttpRequest httpRequest) {
                            httpRequest.setConnectTimeout(4000);
                            httpRequest.setReadTimeout(0);
                        }
                    })
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://97.77.53.74:8888/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            userProfileService = builder.build();
        }

        context = params[0].first;
        UserProfileModel userProfile = params[0].second;
        UserProfile newUserP;
        UserProfileMessage message;

        try {
            message = userProfileService.register(userProfile.username, userProfile.email, userProfile.password).execute();
        } catch (IOException e) {
            return false;
        }

        if (message != null && message.getIsSuccess()) {
            newUserP = (UserProfile) message.getSuccessEntity();

            return true;
        } else
            return false;
    }
}