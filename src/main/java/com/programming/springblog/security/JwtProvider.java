package com.programming.springblog.security;

import com.programming.springblog.exception.SpringBlogException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init(){
        try {

            keyStore = KeyStore.getInstance("JKS");

            Resource resource = new ClassPathResource("/springblog.jks", getClass());
            InputStream resourceAsStream = resource.getInputStream();
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e){
            throw new SpringBlogException("Exception occured while loading keystore");
        }
    }

    public String generateToken(Authentication authentication){
        User principal = (User)authentication.getPrincipal();
        return Jwts.builder()
                    .setSubject(principal.getUsername())
                    .signWith(getPrivateKey())
                    .compact();
    }

    private Key getPrivateKey(){
        try{
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e){
            throw new SpringBlogException("Exception occured while retrieving public key from keystore");
        }

    }

    public boolean validateToken(String jwt){
        Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey(){
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringBlogException("Exception occured while retrieving public key from keystore");
        }
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
