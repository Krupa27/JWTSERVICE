package com.example.jwt.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class Jwtservice {
	@Value("${jwt.secret}") 
    private String SECRET;
	
	public String generateToken(String username) {
		Map<String,Object> claims = new HashMap<>();
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*3))
				.signWith(getKey(),SignatureAlgorithm.HS256).compact();
	}

	private Key getKey() {
		
		byte[] arr = Decoders.BASE64.decode(SECRET);
		
		return Keys.hmacShaKeyFor(arr);
	}
	
	

    // Extracts the username from the token
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Generic method to extract a specific claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    	final Claims claims = extractAllClaims(token);
    	return claimsResolver.apply(claims);
    }
    
    // Extracts all claims from the token
    private Claims extractAllClaims(String token) {
    	return Jwts.parser()
    			.setSigningKey(getKey())
    			.build()
    			.parseClaimsJws(token)
    			.getBody();
    }
    
   



    

    // Validates the token against the UserDetails
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    
 // Checks if the token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    
    
    // Extracts the expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }



	
	
	
}
