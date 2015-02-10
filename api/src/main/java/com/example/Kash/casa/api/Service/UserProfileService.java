package com.example.Kash.casa.api.Service;

import com.example.Kash.casa.api.ApiMessages.UserProfileMessage;
import com.example.Kash.casa.api.Domain.Device;
import com.example.Kash.casa.api.Domain.UserProfile;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.datastore.Query;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import static com.google.appengine.api.datastore.Query.Filter;

/**
 * Created by Kash on 1/19/2015.
 */


@Api(name = "userProfileApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.casa.Kash.example.com", ownerName = "api.casa.Kash.example.com", packagePath = ""))
public class UserProfileService {

    @ApiMethod(name = "register")
    public UserProfileMessage registerUser(@Named("username") String username, @Named("email") String email, @Named("password") String password,
                            @Named("deviceName") String deviceName, @Named("height") int height, @Named("width") int width, @Named("id") String id) {

        //Check username
        Filter usernameFilter = new Query.FilterPredicate("username", Query.FilterOperator.EQUAL, username);
        UserProfile usernameProfile = OfyService.ofy().load().type(UserProfile.class).filter(usernameFilter).first().now();
        if (usernameProfile != null)
            return new UserProfileMessage(false, "Username exists", null);

        //check email
        Filter emailFilter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        UserProfile emailProfile = OfyService.ofy().load().type(UserProfile.class).filter(emailFilter).first().now();
        if (emailProfile != null)
            return new UserProfileMessage(false, "Email exists", null);

        //create new profile, device,  and save
        UserProfile up = new UserProfile(username, email, password);
        Device device = new Device(id, deviceName, height, width);
        up.getDevices().add(Key.create(device));
        OfyService.ofy().save().entity(up).now();

        return new UserProfileMessage(true, "All good", up);
    }

    @ApiMethod(name = "signIn")
    public UserProfileMessage logInUser(@Named("usernameEmail") String usernameEmail, @Named("clearTextPassword") String clearTextPassword)
    {
        //retrieve by username
        Filter usernameFilter = new Query.FilterPredicate("username", Query.FilterOperator.EQUAL, usernameEmail);
        Filter emailFilter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, usernameEmail);
        UserProfile usernameProfile = OfyService.ofy().load().type(UserProfile.class).filter(usernameFilter).first().now();
        UserProfile emailProfile = OfyService.ofy().load().type(UserProfile.class).filter(emailFilter).first().now();

        UserProfile profile = usernameProfile == null ? emailProfile : usernameProfile;

        //no user found
        if (profile == null)
            return new UserProfileMessage(false, "User does not exist", null);

        if (!profile.verifyPassword(clearTextPassword))
            return new UserProfileMessage(false, "Password did not match", null);

        //log the log in date
        profile.setLastLogInDate(new Date());
        OfyService.ofy().save().entity(profile).now();

        return new UserProfileMessage(true, "", profile);
    }

}
