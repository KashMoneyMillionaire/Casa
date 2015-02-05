package com.example.kash.casa.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kash.casa.Models.UserProfileModel;
import com.example.kash.casa.R;
import com.example.kash.casa.Services.UserRegisterTask;

/**
 * Created by Kash on 1/18/2015.
 */
public class RegisterFragment extends Fragment {

    private EditText mUsername;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirmed;
    private Button mRegisterButton;

    private UserRegisterTask mRegisterTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        //get UI
        mUsername = (EditText) rootView.findViewById(R.id.register_username);
        mEmail = (EditText) rootView.findViewById(R.id.register_email);
        mPassword = (EditText) rootView.findViewById(R.id.register_password);
        mPasswordConfirmed = (EditText) rootView.findViewById(R.id.register_password_confirm);
        mPasswordConfirmed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    handled = true;
                    registerUser();
                }
                return handled;
            }
        });

        mRegisterButton = (Button) rootView.findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        return rootView;
    }

    private void registerUser() {
        if (mRegisterTask != null) {
            return;
        }

        // Reset errors.
        mUsername.setError(null);
        mEmail.setError(null);
        mPassword.setError(null);
        mPasswordConfirmed.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String passwordConfirm = mPasswordConfirmed.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check to make sure passwords match
        if (TextUtils.isEmpty(passwordConfirm)){
            mPasswordConfirmed.setError(getString(R.string.error_field_required));
            focusView = mPasswordConfirmed;
            cancel = true;
        } else if (!password.equals(passwordConfirm)) {
            mPasswordConfirmed.setError(getString(R.string.error_password_mismatch));
            focusView = mPasswordConfirmed;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (password.length() < 8 || password.length() > 20) {
            mPassword.setError(getString(R.string.error_password_length));
            focusView = mPassword;
            cancel = true;
        } else if (!password.matches(".*[0-9].*")) {
            mPassword.setError(getString(R.string.error_password_contain_number));
            focusView = mPassword;
            cancel = true;
        } else if (!password.matches(".*[!@#$%&].*")) {
            mPassword.setError(getString(R.string.error_password_contain_character));
            focusView = mPassword;
            cancel = true;
        } else if (!password.matches("[A-Za-z0-9!@#$%&]*")) {
            mPassword.setError(getString(R.string.error_password_character));
            focusView = mPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)){
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!email.contains("@")) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        } else if (username.length() < 3 || username.length() > 16) {
            mUsername.setError(getString(R.string.error_username_length));
            focusView = mUsername;
            cancel = true;
        } else if (!username.matches("[A-Za-z0-9_]*")) {
            mPassword.setError(getString(R.string.error_username_characters));
            focusView = mPassword;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            UserProfileModel upm = new UserProfileModel(username, email, password);

            mRegisterTask = new UserRegisterTask();
            mRegisterTask.execute(new Pair<Context, UserProfileModel>(getActivity(), upm));
        }

    }

}