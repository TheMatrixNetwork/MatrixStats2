package org.matrixnetwork.stats2.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Auth {
    private static Auth instance;
    public long TOKEN_EXPIRATION_TIME = 60 * 5; // in seconds

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    private Auth() {
        Map<String, Object> rsaKeys = null;

        try {
            rsaKeys = getRSAKeys();
        } catch (Exception e) {
            e.printStackTrace();
        }
        publicKey = (PublicKey) rsaKeys.get("public");
        privateKey = (PrivateKey) rsaKeys.get("private");
    }

    public static Auth getInstance() {
        if (instance == null) {
            instance = new Auth();
        }

        return instance;
    }

    private static Map<String, Object> getRSAKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        Map<String, Object> keys = new HashMap<String, Object>();
        keys.put("private", privateKey);
        keys.put("public", publicKey);
        return keys;
    }

    // verify and get claims using public key

    public String generateToken(String username) {
        String token = null;
        try {
            Map<String, Object> claims = new HashMap<String, Object>();

            // put your information into claim
            claims.put("username", username);

            Instant now = Instant.now();

            token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(now.plusSeconds(TOKEN_EXPIRATION_TIME)))
                    .signWith(privateKey)
                    .compact();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public String verifyToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }

        return claims.getSubject();
    }

}
