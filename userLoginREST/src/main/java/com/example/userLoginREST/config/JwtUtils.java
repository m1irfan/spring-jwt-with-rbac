package com.example.userLoginREST.config;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	private Integer EXPIRATION = 60*60*20;
	private String SECRET = "afdglhkadsovianemfbsjkdfgjaksdfnbhasgdalsdbfasgdf";
	private Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));


	public String GenerateToken(String username) {

		return Jwts
				.builder()
				//.claim("username", username)
				.subject(username)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+1000*60*1))
				.signWith(key)
				.compact();

	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);

	}

	private Claims extractAllClaims(String token) {

		return Jwts.parser()
				.verifyWith((SecretKey)key).build()
				.parseSignedClaims(token)
				.getPayload();

	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
