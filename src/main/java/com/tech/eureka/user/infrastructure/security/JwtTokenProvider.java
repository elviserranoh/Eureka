package com.tech.eureka.user.infrastructure.security;

import com.tech.eureka.user.infrastructure.persistance.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Log
@Component
public class JwtTokenProvider {
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	private static final String TOKEN_TYPE = "JWT";

	@Value("${jwt.secret:EnUnLugarDeLaManchaDeCuyoNombreNoQuieroAcordarmeNoHaMuchoTiempoQueViviaUnHidalgo}")
	private String jwtSecret;

	@Value("${jwt.token-expiration:864000}")
	private int jwtDurationToken;

	public String generarToken(Authentication authentication) {
		UserEntity user = (UserEntity) authentication.getPrincipal();
		Date tokenExpirationDate = new Date(System.currentTimeMillis() + (jwtDurationToken * 1000));
		
		return Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
				.setHeaderParam("typ", TOKEN_TYPE)
				.setSubject(String.valueOf(user.getId()))
				.setIssuedAt(new Date())
				.setExpiration(tokenExpirationDate)
				.claim("email", user.getEmail())
				.claim("roles", user.getAuthorities().toString())
				.compact();
	}

	public UUID getUserIdFromJWT(String token) {

		Claims claims = Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
				.build()
				.parseClaimsJws(token)
				.getBody();
		

		return UUID.fromString(claims.getSubject());

	}
	

	public boolean validateToken(String authToken) {
		
		try {
			Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
					.build()
					.parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			log.info("Error en la firma del token JWT: " + ex.getMessage());
		} catch (MalformedJwtException ex) {
			log.info("Token malformado: " + ex.getMessage());
		} catch (ExpiredJwtException ex) {
			log.info("El token ha expirado: " + ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			log.info("Token JWT no soportado: " + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			log.info("JWT claims vacío");
		}
        return false;
		
		
		
	}

}
