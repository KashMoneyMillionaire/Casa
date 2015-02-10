package com.example.Kash.casa.api.Service;

import com.example.Kash.casa.api.Domain.CasaEntity;
import com.example.Kash.casa.api.Domain.Device;
import com.example.Kash.casa.api.Domain.Photo;
import com.example.Kash.casa.api.Domain.UserProfile;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Created by Kash on 1/13/2015.
 */
public class OfyService {
    static {
        factory().register(CasaEntity.class);
        factory().register(UserProfile.class);
        factory().register(Device.class);
        factory().register(Photo.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
