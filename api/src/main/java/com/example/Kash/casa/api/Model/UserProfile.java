package com.example.Kash.casa.api.Model;

import com.example.Kash.casa.api.Helpers.SaltedHash;
import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The object model for the data we are sending through endpoints
 */

@Subclass(name = "User")
public class UserProfile extends CasaEntity {

    private String Username;
    private String Email;
    private String PasswordSalt;
    private String PasswordHash;
    private Date CreatedDate;

    @Index
    private List<Key<Device>> Devices = new ArrayList<>();

    public void setPassword(String clearTextPassword){
        SaltedHash saltedHash = new SaltedHash(clearTextPassword);
        PasswordHash = saltedHash.Hash;
        PasswordSalt = saltedHash.Salt;
    }

    public boolean verifyPassword(String clearTextPassword){
        return new SaltedHash(this.PasswordSalt, this.PasswordHash).verify(clearTextPassword);
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public List<Key<Device>> getDevices() {
        return Devices;
    }

    public String getPasswordSalt() { return PasswordSalt; }

    public String getPasswordHash() { return PasswordHash; }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }
}