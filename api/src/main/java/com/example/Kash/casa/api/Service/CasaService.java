/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.Kash.casa.api.Service;

import com.example.Kash.casa.api.Domain.UserProfile;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "casaApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.casa.Kash.example.com", ownerName = "api.casa.Kash.example.com", packagePath = ""))
public class CasaService {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sendPhoto")
    public UserProfile sendPhoto(@Named("userName") String userName) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(userName);
        userProfile.setEmail(userName);
        userProfile.setPassword("asdf");

        UserProfile u = OfyService.ofy().load().type(UserProfile.class).id(5629499534213120L).now();
        OfyService.ofy().save().entity(userProfile).now();

//        Logger log = Logger.getLogger(DatastoreApiServlet.class.getName());
//        log.info("Info");
//        log.warning("Warning");
        return userProfile;
    }

}
