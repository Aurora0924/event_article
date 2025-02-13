package com.wlw;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JwtTest {

    @Test
    public void testGen() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "admin");
        claims.put("password", "123456");
        //1.生成token
        String token = JWT.create().withClaim("user", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .sign(Algorithm.HMAC256("wlw"));
        System.out.println(token);
    }

    @Test
    public void testParse() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7InBhc3N3b3JkIjoiMTIzNDU2IiwidXNlcm5hbWUiOiJhZG1pbiJ9LCJleHAiOjE3Mzk0NzUwNjZ9." +
                "-6EMbUE28NqBzzrPFY-ZIYCC1DI06F6C_I3efLCpKIU";
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("wlw")).build().verify(token);
        System.out.println(decodedJWT.getClaim("user").asMap());
    }
}
