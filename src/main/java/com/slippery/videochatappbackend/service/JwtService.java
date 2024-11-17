package com.slippery.videochatappbackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRETSTRING ="c09e09407f7f53c073079a6153daf21749d4436c079053ba6ea9759d2759f165e59850cb6d80b0359aa01192776f7843a2d81bc6a3dbf581cba5e78f250a417491d0e51720da9c7a6bee2dff3f4df1fabb674245c9971f7f2114e28ff107b0dc7d360fa46f6c5377b31d91900d5e1648d98c1766ab229604e6c54ee646812df17e69be92b9c8a75b89ebc2f724ee66ea204859a6393703de9e6f0cedceec30a68860139c1dc1897aac71b85a745d0148ebcabd14deb6f4ab2a60fa3dd8626754a4c03ecb75da794804bb4cb6966cf8787d05ee9c5185ddfd300dcb730b93e1aec8a98e175d01c28c700c2cae481b1982ae78b77607620126a576076a9a832c86";
    private final Long EXPIRYTIME =86400000L;

    protected SecretKey generateSecretKey(){
//        byte[] keyBytes = Decoders.BASE64.decode(SECRETSTRING);
        byte[] bytes = Base64.getDecoder().decode(SECRETSTRING);
        return Keys.hmacShaKeyFor(bytes);
    }
    public String generateJwtToken(String username){
        Map<String,Object> claims =new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+EXPIRYTIME))
                .and()
                .signWith(generateSecretKey())
                .compact();
    }
//    public JwtKeyDto refreshJwtToken(String username){
//        JwtKeyDto response =new JwtKeyDto();
//        Map<String,Object> claims =new HashMap<>();
//        var jwtTk = Jwts.builder()
//                .claims()
//                .add(claims)
//                .subject(username)
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis()+EXPIRYTIME))
//                .and()
//                .signWith(generateSecretKey())
//                .compact();
//        response.setJwtKey(jwtTk);
//        return response;
//    }
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(generateSecretKey()).build().parseSignedClaims(token).getPayload());

    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}

