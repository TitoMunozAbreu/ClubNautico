package com.app.clubnautico.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Manipular jwt token, generar token, extraer informacion del token
 *
 */
@Service
public class JwtService {
    //minmun 256-bit
    private static final String SECRECT_KEY = "46294A404E635266556A576E5A7234753778214125442A472D4B615064536756";

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    //metodo que pueda validar un token
    public boolean isTokenValid(String token,
                                UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()))&& !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    //metodo para generar un token con solo userDetails
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    //metodo para generar un token con extraClaims y userDetails
    public String generateToken(//contine los claims que queremos añadir
                                Map<String,Object> extraClaims,
                                UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) //en este caso es el email para nuestra clase de user
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *24)) //sera valido por 24h + 1000 miliseconds
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //extrar un claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //extraer todos los claims
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                //cuando generated or decoded un token se necesita un signingKey
                .setSigningKey(getSignInKey()) //firma digitalmente JWT para verficar que el sender del JWT es quien dice ser y msg no haya sido cambiado
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRECT_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
