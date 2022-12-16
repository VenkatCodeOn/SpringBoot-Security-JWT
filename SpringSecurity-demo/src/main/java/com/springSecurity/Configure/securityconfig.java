package com.springSecurity.Configure;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springSecurity.services.CustomService;





@Configuration
@EnableWebSecurity
public class securityconfig extends  WebSecurityConfigurerAdapter {

	@Autowired
	private CustomService userService;
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(auth);
		//For in memory authentication
		auth.inMemoryAuthentication().withUser("Venkat").password(passwordencoder().encode("12312345")).authorities("USER","ADMIN");
	
		//Database Authentication
		auth.userDetailsService(userService).passwordEncoder(passwordencoder());
	}

    @Bean
    public PasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		//Any request comes from this website to permit to open all 
		//http.authorizeRequests().anyRequest().permitAll();
	
		//http.authorizeRequests((request)->request.antMatchers("/h2-console/**").permitAll().anyRequest().authenticated()).httpBasic();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
		.authenticationEntryPoint(authenticationEntryPoint).and()
		.authorizeRequests((request)->request.antMatchers("/h2-console/**","/api/v1/auth/login").permitAll().antMatchers(HttpMethod.OPTIONS,"/**").permitAll().anyRequest()
		.authenticated()).addFilterBefore(new JWTAuthenticationFilter(userService, jwtTokenHelper),UsernamePasswordAuthenticationFilter.class);
		
		//http.cors();
		//http.formLogin();
		//H2 Console
		http.csrf().disable().cors().and().headers().frameOptions().disable();
		
		/* http.httpBasic(); */
	}
 
	
	
}
