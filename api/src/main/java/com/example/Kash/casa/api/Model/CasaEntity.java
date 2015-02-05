package com.example.Kash.casa.api.Model;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


/**
 * Created by Kash on 1/13/2015.
 */

@Entity(name = "CasaEntity")
public class CasaEntity {

    @Id
    private Long Key;

    public Long getKey() {
        return Key;
    }
    public void setKey(Long key) {
        Key = key;
    }
}
