package com.springSecurity.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springSecurity.Entities.Users;
import com.springSecurity.repository.UserDetailsRepository;
@Service
public class CustomService implements UserDetailsService{

	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users users=userDetailsRepository.findByUsername(username);
		if(null==users) {
			throw new UsernameNotFoundException("User Not Found");
		}
		return users;
	}
	

}
