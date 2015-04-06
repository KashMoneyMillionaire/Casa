package com.example.kash.casa.Helpers;

import com.example.kash.casa.Mock.MockUserProfileApi;
import com.example.kash.casa.api.userProfileApi.UserProfileApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;

import java.io.IOException;

/**
 * Created by Kash on 2/14/2015.
 */
public class UserProfileApiFactory {

    private static UserProfileApi instance;

    private UserProfileApiFactory() {}

//    public synchronized static UserProfileApi getApi(){
//        if (instance != null) return instance;
//
//        UserProfileApi.Builder builder = new UserProfileApi.Builder(AndroidHttp.newCompatibleTransport(),
//                new AndroidJsonFactory(),
//                //TODO remove this when not debugging
//                new HttpRequestInitializer() {
//                    public void initialize(HttpRequest httpRequest) {
//                        httpRequest.setConnectTimeout(4000);
//                        httpRequest.setReadTimeout(0);
//                    }
//                })
//                // options for running against local devappserver
//                // - 10.0.2.2 is localhost's IP address in Android emulator
//                // - turn off compression when running against local devappserver
//                .setRootUrl("http://97.77.53.74:8888/_ah/api/")
//                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                    @Override
//                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                        abstractGoogleClientRequest.setDisableGZipContent(true);
//                    }
//                });
//        // end options for devappserver
//
//        return instance = builder.build();
//    }

    public synchronized static UserProfileApi getApi(){
        if (instance != null) return instance;

        return new MockUserProfileApi();
    }
}
