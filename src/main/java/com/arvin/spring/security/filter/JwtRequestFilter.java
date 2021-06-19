package com.arvin.spring.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.arvin.spring.security.service.MyUserDetailService;
import com.arvin.spring.security.utility.JwtUtil;
@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	@Autowired
	private MyUserDetailService userDetailService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Override 
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response , FilterChain filter ) throws ServletException , IOException 
	{
	  String jwt = null;
	  String username = null;
	  final String header = request.getHeader("Authorization");
	  if (header != null && header.startsWith("Bearer "))
	  {
		  jwt = header.substring(7);
	      username = jwtTokenUtil.extractUsername(jwt);
	  }
	  if ( username != null && SecurityContextHolder.getContext().getAuthentication() == null )
      {
      UserDetails userDetail = this.userDetailService.loadUserByUsername(username);
      if (jwtTokenUtil.validateToken(jwt, userDetail))
      {
    	  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
    			  userDetail,null,userDetail.getAuthorities());
    	  usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    	  SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
   
      }
      }
	  filter.doFilter(request, response);
	}
}