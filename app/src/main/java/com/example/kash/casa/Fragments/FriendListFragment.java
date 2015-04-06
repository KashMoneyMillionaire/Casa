package com.example.kash.casa.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kash.casa.FriendListAdapter;
import com.example.kash.casa.Helpers.CasaPreferenceManager;
import com.example.kash.casa.Helpers.UserProfileApiFactory;
import com.example.kash.casa.Models.UserProfileModel;
import com.example.kash.casa.R;
import com.example.kash.casa.api.userProfileApi.UserProfileApi;
import com.example.kash.casa.api.userProfileApi.model.HomieListMessage;
import com.example.kash.casa.api.userProfileApi.model.UserProfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Kash on 2/15/2015.
 * Do work homie.
 */
public class FriendListFragment extends Fragment
{
    @InjectView(R.id.friend_list_recycler) RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_friend_list, container, false);
        ButterKnife.inject(this, rootView);
        context = getActivity();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new FriendListTask().execute((Void) null);

        return rootView;
    }



    public class FriendListTask extends AsyncTask<Void, Void, List<UserProfile>>
    {
        @Override
        protected List<UserProfile> doInBackground(Void... params) {
            CasaPreferenceManager cpm = new CasaPreferenceManager(context,
                                                                  CasaPreferenceManager.PrefType.Login);
            UserProfileApi api = UserProfileApiFactory.getApi();
            try
            {
                HomieListMessage message = api.getHomieList(cpm.getUsername()).execute();
                return message.getUserProfiles();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<UserProfile> result) {
            // specify an adapter (see also next example)
            mAdapter = new FriendListAdapter(result);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
