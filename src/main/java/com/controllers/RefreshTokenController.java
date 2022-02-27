package com.controllers;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
@RestController
@RequestMapping("/refreshToken")
@Slf4j
public class RefreshTokenController {
	@Autowired
	com.services.Implementations.UserService userService;
	
	@GetMapping("/getAcces")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String authorizationHeader = request.getHeader(AUTHORIZATION);
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
			try{
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				User user = userService.retrieveUserByUsername(username);
				
				String access_token = JWT.create()
						.withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURI().toString())
						.withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
						.sign(algorithm);
				Map<String , String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch (Exception exception){
				log.error("Error logging in : {}", exception.getMessage());
				response.setHeader("error", exception.getMessage());
				Map<String , String> error = new HashMap<>();
				error.put("error_message", exception.getMessage());
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
			
		} else {
			throw new RuntimeException("Refresh token is missing");
		}

	}
}
