package com.crud.simple.config;

import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.nimbusds.jose.jwk.RSAKey;

import lombok.var;

@Component
public class KeyManager {
    
    public RSAKey rsaKey() {

        try{
            KeyPairGenerator k = KeyPairGenerator.getInstance("RSA");
            k.initialize(2048);
            var kp = k.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
            return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

}
