package com.example.Kash.casa.api.ApiMessages;

import com.example.Kash.casa.api.Domain.UserProfile;

/**
 * Created by Kash on 1/19/2015.
 */
public class UserProfileMessage {
    public boolean IsSuccess;
    public String Message;
    public UserProfile SuccessEntity;

    public UserProfileMessage(boolean success, String message, UserProfile entity) {
        IsSuccess = success;
        Message = message;
        SuccessEntity = entity;
    }
}
