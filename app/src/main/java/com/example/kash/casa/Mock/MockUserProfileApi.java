package com.example.kash.casa.Mock;

import com.example.kash.casa.api.userProfileApi.UserProfileApi;
import com.example.kash.casa.api.userProfileApi.model.HomieListMessage;
import com.example.kash.casa.api.userProfileApi.model.UserProfile;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kash on 2/19/2015.
 * Do work homie.
 */
public class MockUserProfileApi extends UserProfileApi
{
    public MockUserProfileApi()
    {
        super(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
    }

    @Override
    public GetHomieList getHomieList(String username) throws IOException
    {
        return new MockGetHomieList(username);
    }

    private class MockGetHomieList extends GetHomieList{

        protected MockGetHomieList(String username)
        {
            super(username);
        }

        @Override
        public HomieListMessage execute() throws IOException
        {
            String[] names = {"KashMoney", "Travie", "HxCSLayer"};
            List<UserProfile> homies = new ArrayList<>();

            for (String name : names)
            {
                UserProfile up = new UserProfile();
                up.setUsername(name);
                homies.add(up);
            }

            HomieListMessage message = new HomieListMessage();
            message.setUserProfiles(homies);
            return message;
        }
    }
}
