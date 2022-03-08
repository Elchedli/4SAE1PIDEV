package com.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.entities.User;
import com.services.Implementations.UserService;
@Service
public class DecodeToken {
	@Autowired
	UserService us;
	
	public User decode(String token) {
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		String username = decodedJWT.getSubject();
		return us.retrieveByUsername(username);
	}
}
