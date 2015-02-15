package com.example.kash.casa.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kash.casa.Helpers.Device;
import com.example.kash.casa.Helpers.LoginHelper;
import com.example.kash.casa.Models.UserProfileModel;
import com.example.kash.casa.R;
import com.example.kash.casa.Tasks.UserRegisterTask;
import com.example.kash.casa.api.userProfileApi.model.UserProfile;

import butterknife.InjectView;

/**
 * Created by Kash on 1/18/2015.
 */
public class RegisterFragment extends Fragment {

    // UI references.
    @InjectView(R.id.register_username)
    EditText mUsername;
    @InjectView(R.id.register_email)
    EditText mEmail;
    @InjectView(R.id.register_password)
    EditText mPassword;
    @InjectView(R.id.register_password_confirm)
    EditText mPasswordConfirmed;
    @InjectView(R.id.register_button)
    Button mRegisterButton;

    private UserRegisterTask mRegisterTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout containing a title and body text.
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        //set actions on elements
        mPasswordConfirmed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handled = true;
                    registerUser();
                }
                return handled;
            }
        });
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

        //verify form
        boolean goodToGo = verifyForm();
        if (!goodToGo) return;

        //get userProfile information
        UserProfileModel upm = new UserProfileModel("email", "username", "password");
        upm.Device = Device.getDeviceInfo(getActivity());

        mRegisterTask = new UserRegisterTask() {
            @Override
            protected void onPostExecute(UserProfile result) {
                if (result != null) {
                    Toast.makeText(getActivity(), "Registration complete", Toast.LENGTH_SHORT).show();
                    LoginHelper.setLoginPreferencesAndContinue(result, getActivity());
                } else {
                    Toast.makeText(getActivity(), "Registration failure", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void onCancelled() {
                mRegisterTask = null;
            }
        };
        mRegisterTask.execute(upm);
    }

    private boolean verifyForm() {

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

        boolean errorsExist = false;
        View focusView = null;

        // Check to make sure passwords match
        if (TextUtils.isEmpty(passwordConfirm)) {
            mPasswordConfirmed.setError(getString(R.string.error_field_required));
            focusView = mPasswordConfirmed;
            errorsExist = true;
        } else if (!password.equals(passwordConfirm)) {
            mPasswordConfirmed.setError(getString(R.string.error_password_mismatch));
            focusView = mPasswordConfirmed;
            errorsExist = true;
        }

        // Check for a valid password, if the user entered one.
        if (password.length() < 8 || password.length() > 20) {
            mPassword.setError(getString(R.string.error_password_length));
            focusView = mPassword;
            errorsExist = true;
        } else if (!password.matches(".*[0-9].*")) {
            mPassword.setError(getString(R.string.error_password_contain_number));
            focusView = mPassword;
            errorsExist = true;
        } else if (!password.matches(".*[!@#$%&].*")) {
            mPassword.setError(getString(R.string.error_password_contain_character));
            focusView = mPassword;
            errorsExist = true;
        } else if (!password.matches("[A-Za-z0-9!@#$%&]*")) {
            mPassword.setError(getString(R.string.error_password_character));
            focusView = mPassword;
            errorsExist = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            errorsExist = true;
        } else if (!email.contains("@")) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            errorsExist = true;
        }

        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            errorsExist = true;
        } else if (username.length() < 3 || username.length() > 16) {
            mUsername.setError(getString(R.string.error_username_length));
            focusView = mUsername;
            errorsExist = true;
        } else if (!username.matches("[A-Za-z0-9_]*")) {
            mPassword.setError(getString(R.string.error_username_characters));
            focusView = mPassword;
            errorsExist = true;
        }

        //update form
        if (errorsExist) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

        return !errorsExist;
    }

}
