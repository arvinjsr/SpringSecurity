package com.arvin.spring.security.Repository;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class User {

	@Id
	private Long id;
	private String username;
	private String password;
	
	public User(Long id, String uername, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	public User() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
