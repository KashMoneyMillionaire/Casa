package com.example.Kash.casa.api.Service;

import com.example.Kash.casa.api.ApiMessages.HomieListMessage;
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


@Api(name = "userProfileApi", version = "v1",
     namespace = @ApiNamespace(ownerDomain = "api.casa.Kash.example.com",
                               ownerName = "api.casa.Kash.example.com", packagePath = ""))
public class UserProfileService
{

    @ApiMethod(name = "register")
    public UserProfileMessage registerUser(@Named("username") String username,
                                           @Named("email") String email,
                                           @Named("password") String password,
                                           @Named("deviceName") String deviceName,
                                           @Named("height") int height, @Named("width") int width,
                                           @Named("id") String id)
    {

        //Check username
        Filter usernameFilter = new Query.FilterPredicate("username", Query.FilterOperator.EQUAL,
                                                          username);
        UserProfile usernameProfile = OfyService.ofy().load().type(UserProfile.class).filter(
                usernameFilter).first().now();
        if (usernameProfile != null)
            return new UserProfileMessage(false, "Username exists", null);

        //check email
        Filter emailFilter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        UserProfile emailProfile = OfyService.ofy().load().type(UserProfile.class).filter(
                emailFilter).first().now();
        if (emailProfile != null)
            return new UserProfileMessage(false, "Email exists", null);

        //create new profile, device,  and save
        UserProfile up = new UserProfile(username, email, password);
        Device device = new Device(id, deviceName, height, width);
        device.setUser(up);
        up.addDevice(device);
        OfyService.ofy().save().entity(up).now();

        return new UserProfileMessage(true, "", up);
    }

    @ApiMethod(name = "signIn")
    public UserProfileMessage logInUser(@Named("usernameEmail") String usernameEmail,
                                        @Named("clearTextPassword") String clearTextPassword,
                                        @Named("deviceName") String deviceName,
                                        @Named("height") int height, @Named("width") int width,
                                        @Named("id") String id)
    {
        UserProfile profile = getUserProfile(usernameEmail, UserProfile.Dev.class);

        //no user found
        if (profile == null)
            return new UserProfileMessage(false, "User does not exist", null);

        if (!profile.verifyPassword(clearTextPassword))
            return new UserProfileMessage(false, "Password did not match", null);

        //log the log in date
        profile.setLastLogInDate(new Date());

        //check if the signed in device exists
        boolean containsDevice = false;
        for (Device d : profile.getDevices())
        {
            if (d.getAndroidId().equals(id))
            {
                containsDevice = true;
                break;
            }
        }
        if (!containsDevice)
        {
            Device d = new Device(id, deviceName, height, width);
            d.setUser(profile);
            profile.addDevice(d);
        }

        //save changes
        OfyService.ofy().save().entity(profile).now();

        return new UserProfileMessage(true, "", profile);
    }


    @ApiMethod(name = "getHomieList")
    public HomieListMessage getHomies(@Named("username") String username)
    {
        UserProfile userProfile = getUserProfile(username, UserProfile.Homie.class );
        return new HomieListMessage(userProfile.getHomies());
    }


    private UserProfile getUserProfile(String emailUsername, Class<?>... groups)
    {
        //retrieve by username
        Filter usernameFilter = new Query.FilterPredicate("username", Query.FilterOperator.EQUAL,
                                                          emailUsername);
        Filter emailFilter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL,
                                                       emailUsername);
        UserProfile usernameProfile = OfyService.ofy().load().group(groups).type(UserProfile.class).filter(
                usernameFilter).first().now();
        UserProfile emailProfile = OfyService.ofy().load().group(groups).type(UserProfile.class).filter(
                emailFilter).first().now();

        return usernameProfile == null ? emailProfile : usernameProfile;
    }
}
