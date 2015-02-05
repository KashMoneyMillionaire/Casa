package com.example.Kash.casa.api.Helpers;

/**
 * Created by Kash on 1/13/2015.
 */

import com.google.appengine.repackaged.com.google.api.client.util.Base64;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SaltedHash {
    // The higher the number of iterations the more
    // expensive computing the hash is for us and
    // also for an attacker.
    private static final int iterations = 20;
    private static final int saltLen = 24;
    private static final int desiredKeyLen = 256;

    public String Hash;
    public String Salt;

    public SaltedHash(String clearTextPassword){
        Salt = createSalt();
        Hash = calculateHash(Salt, clearTextPassword);
    }

    public SaltedHash(String salt, String hash) {
        this.Salt = salt;
        this.Hash = hash;
    }

    public boolean verify(String clearTextPassword) {
        return Hash.equals(calculateHash(Salt, clearTextPassword));
    }

    private String calculateHash(String salt, String clearTextPassword) {
        SecretKey key = null;
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            key = f.generateSecret(new PBEKeySpec(
                            clearTextPassword.toCharArray(), salt.getBytes(), iterations, desiredKeyLen)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(key.getEncoded());
    }

    private String createSalt() {
        byte[] salt = new byte[0];
        try {
            salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(salt);
    }
}
