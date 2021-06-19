package com.arvin.spring.security.Configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.arvin.spring.security.filter.JwtRequestFilter;

@Configuration
@EnableWebSecurity 
public class SpringSecuityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	/*
	 * @Bean
	 * 
	 * @Override protected UserDetailsService userDetailsService() {
	 * List<UserDetails> user = new ArrayList<>();
	 * user.add(User.withDefaultPasswordEncoder().username("arvin").password("arvin"
	 * ).roles("User").build()); return new InMemoryUserDetailsManager(user); }
	 */
	@Override 
	protected void configure(HttpSecurity http) throws Exception 
	{
		http.csrf().disable().authorizeRequests().antMatchers("/Auth").permitAll().anyRequest().authenticated()
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}
	
	@Bean
	public AuthenticationProvider provider()
	{
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userDetailsService);
		auth.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return auth;
	}

}
