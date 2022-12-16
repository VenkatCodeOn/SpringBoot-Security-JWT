package com.springSecurity;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.springSecurity.Entities.Authority;
import com.springSecurity.Entities.Users;
import com.springSecurity.repository.UserDetailsRepository;
import java.util.ArrayList;

@SpringBootApplication
public class SpringSecurityDemoApplication {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemoApplication.class, args);
	}
	
	@PostConstruct
	protected void init() {
		List<Authority> authorityList=new ArrayList<>();
		
		authorityList.add(createAuthority("USER","User role"));
		authorityList.add(createAuthority("ADMIN","Admin role"));
	
		Users users=new Users();
		users.setUsername("venkat12");
		users.setFirstname("Venkatesh");
		users.setLastname("Babu");
		
		users.setPassword(passwordEncoder.encode("Venkat@1"));
		users.setEnabled(true);
		users.setAuthorities(authorityList);
		
		userDetailsRepository.save(users);
		
	}
	
	private Authority createAuthority(String roleCode,String roleDescription) {
		Authority authority=new Authority();
		authority.setRole_code(roleCode);
		authority.setRole_decription(roleDescription);
		return authority;
		
	}

}
