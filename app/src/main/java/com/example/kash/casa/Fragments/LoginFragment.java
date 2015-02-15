package com.example.kash.casa.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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

import com.example.kash.casa.Helpers.LoginHelper;
import com.example.kash.casa.Models.UserProfileModel;
import com.example.kash.casa.R;
import com.example.kash.casa.Tasks.UserLoginTask;
import com.example.kash.casa.api.userProfileApi.model.UserProfile;

import butterknife.InjectView;

/**
 * Created by Kash on 1/18/2015.
 */
public class LoginFragment extends Fragment {

    // UI references.
    @InjectView(R.id.login_email)
    EditText mEmailView;
    @InjectView(R.id.login_password)
    EditText mPasswordView;
    @InjectView(R.id.login_progress)
    View mProgressView;
    @InjectView(R.id.login_form)
    View mLoginFormView;
    @InjectView(R.id.login_button)
    Button mLoginButton;

    private UserLoginTask mAuthTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout containing a title and body text.
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // Set up the login form.
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        return rootView;
    }

    private boolean verifyForm(String email, String password) {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);


        boolean errorsExist = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            errorsExist = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            errorsExist = true;
        }


        if (errorsExist) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

        return !errorsExist;
    }

    public void showProgress(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean isFormValid = verifyForm(email, password);
        if (!isFormValid) return;

        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        showProgress(true);
        UserProfileModel model = new UserProfileModel(email, email, password);

        mAuthTask = new UserLoginTask() {
            @Override
            protected void onPostExecute(UserProfile result) {
                mAuthTask = null;
                showProgress(false);

                if (result != null) {
                    LoginHelper.setLoginPreferencesAndContinue(result, getActivity());
                }
            }

            @Override
            protected void onCancelled() {
                mAuthTask = null;
                showProgress(false);
            }
        };
        mAuthTask.execute(model);
    }
}
