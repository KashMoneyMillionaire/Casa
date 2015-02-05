package com.example.Kash.casa.api.Service;

import com.example.Kash.casa.api.Helpers.ApiMessage;
import com.example.Kash.casa.api.Model.UserProfile;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.Date;

import javax.inject.Named;

/**
 * Created by Kash on 1/19/2015.
 */


@Api(name = "userProfileApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.casa.Kash.example.com", ownerName = "api.casa.Kash.example.com", packagePath = ""))
public class UserProfileService {

    @ApiMethod(name = "registerUser")
    public ApiMessage registerUser(@Named("username") String username, @Named("email") String email, @Named("password") String password){

        //TODO make sure no username/email already exist


        UserProfile up = new UserProfile();
        up.setUsername(username);
        up.setEmail(email);
        up.setPassword(password);
        up.setCreatedDate(new Date());

        ApiMessage apiMessage = new ApiMessage();
        apiMessage.isSuccess = true;
        apiMessage.successEntity = up;
        apiMessage.message = "All good";

        return apiMessage;
    }

}
