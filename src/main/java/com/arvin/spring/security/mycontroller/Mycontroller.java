package com.arvin.spring.security.mycontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.arvin.spring.security.model.AuthenticationRequest;
import com.arvin.spring.security.model.AuthenticationResponse;
import com.arvin.spring.security.service.MyUserDetailService;
import com.arvin.spring.security.utility.JwtUtil;

@Controller
public class Mycontroller {

	@Autowired
	private AuthenticationManager  authenticationManager;
	
	@Autowired
	private MyUserDetailService userDetailService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@RequestMapping("/Test")
	public String Home(Model model) {
		System.out.println("Welcome Home !!!");
		return "Home.html";
	}
	
	@RequestMapping(value = "/Auth" , method = RequestMethod.POST)
	public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authenticationRequest)
	{
		authenticationManager.authenticate(
				(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())));
		final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
}

