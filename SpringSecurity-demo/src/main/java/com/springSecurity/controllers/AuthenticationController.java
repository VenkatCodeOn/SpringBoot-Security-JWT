package com.springSecurity.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springSecurity.Configure.JWTTokenHelper;
import com.springSecurity.Entities.Users;
import com.springSecurity.requests.AuthenticationRequest;
import com.springSecurity.response.LoginResponse;
import com.springSecurity.response.UserInfo;

@RestController
	@RequestMapping("/api/v1")
	@CrossOrigin
	public class AuthenticationController {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Autowired
		JWTTokenHelper jWTTokenHelper;
		
		@Autowired
		private UserDetailsService userDetailsService;

		@PostMapping("/auth/login")
		public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {

			final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUserName(), authenticationRequest.getPassword()));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			Users users=(Users)authentication.getPrincipal();
			String jwtToken=jWTTokenHelper.generateToken(users.getUsername());
			
			LoginResponse response=new LoginResponse();
			response.setToken(jwtToken);
			

			return ResponseEntity.ok(response);
		}
		
		@GetMapping("/auth/userinfo")
		public ResponseEntity<?> getUserInfo(Principal users){
			Users userObj=(Users) userDetailsService.loadUserByUsername(users.getName());
			
			UserInfo userInfo=new UserInfo();
			userInfo.setFirstName(userObj.getFirstname());
			userInfo.setLastName(userObj.getLastname());
			userInfo.setRoles(userObj.getAuthorities().toArray());
			
			
			return ResponseEntity.ok(userInfo);
			
			
			
		}
}
