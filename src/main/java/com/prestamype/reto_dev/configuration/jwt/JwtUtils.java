package com.prestamype.reto_dev.configuration.jwt;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {

	@Value("${security.jwt.key.private}")
	private String privateKey; // sello de la firma, para validar quien es el que genero el token
	
	@Value("${security.jwt.user.generator}")
	private String userGenerator;
	
	@Value("${jwt.time.expiration}")
	private String timeExpiration;
	
	// generar token de acceso
	public String createToken(Authentication authentication) {
		
		Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
		
		String email = authentication.getPrincipal().toString();
		String authorities = authentication.getAuthorities()
				.stream()
				.map(grantedAuthoritie -> grantedAuthoritie.getAuthority()) // .map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		String jwtToken = JWT.create()
				.withIssuer(this.userGenerator)
				.withSubject(email)
				.withClaim("authorities", authorities)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
				.withJWTId(UUID.randomUUID().toString())
				.withNotBefore(new Date(System.currentTimeMillis()))
				.sign(algorithm);
		
		return jwtToken;
		
	}
	
	// para validar el token
	public DecodedJWT validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
			
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(this.userGenerator)
					.build();
			
			DecodedJWT decodedJWT =  verifier.verify(token); // retornar JWT decodificado
			return decodedJWT;
			
		} catch (JWTVerificationException e) {
			throw new JWTVerificationException("Token invalido, no autorizado");
		}
	}
	
	public String extractUsername(DecodedJWT decodedJWT) {
		return decodedJWT.getSubject().toString();
	}
	
	public String extractEmail(DecodedJWT decodedJWT) {
		return decodedJWT.getSubject().toString();
		
		//return decodedJWT.getClaim("email").toString();
	}
	
	public Claim getSpecificClaimDecodedJWT(DecodedJWT decodedJWT, String claimName) {
		return decodedJWT.getClaim(claimName);
	}
	
	public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT){
		return decodedJWT.getClaims();
	}
}
