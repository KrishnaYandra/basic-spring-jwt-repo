package com.krishna.sample.spring.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.krishna.sample.spring.jwt.model.JwtRequest;
import com.krishna.sample.spring.jwt.model.JwtResponce;
import com.krishna.sample.spring.jwt.service.UserService;
import com.krishna.sample.spring.jwt.utility.JWTUtility;

@RestController
public class HomeController {

	@Autowired
	private JWTUtility jwtUtility;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@GetMapping("/")
	public String home() {
		return "Sample Spring JWT";
	}

	@PostMapping("/authenticate")
	public JwtResponce athenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
		}catch(BadCredentialsException e) {
			throw new Exception("INVALID CREDENTIALS",e);
		}
		final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUserName());
		
		final String token = jwtUtility.generateToken(userDetails);
		
		return new JwtResponce(token);
	}
}
