package io.github.tuanpq.java.spring.jwt.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignupRequest {
	
	private static final Logger logger = LoggerFactory.getLogger(SignupRequest.class);
	
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	private Set<String> role;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

	public String getUsername() {
		logger.debug("getUsername enter");
		return username;
	}

	public void setUsername(String username) {
		logger.debug("setUsername enter");
		this.username = username;
	}

	public String getEmail() {
		logger.debug("getEmail enter");
		return email;
	}

	public void setEmail(String email) {
		logger.debug("setEmail enter");
		this.email = email;
	}

	public String getPassword() {
		logger.debug("getPassword enter");
		return password;
	}

	public void setPassword(String password) {
		logger.debug("setPassword enter");
		this.password = password;
	}

	public Set<String> getRole() {
		logger.debug("getRole enter");
		return this.role;
	}

	public void setRole(Set<String> role) {
		logger.debug("setRole enter");
		this.role = role;
	}

}
