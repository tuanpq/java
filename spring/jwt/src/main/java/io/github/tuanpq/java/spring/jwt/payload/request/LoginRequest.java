package io.github.tuanpq.java.spring.jwt.payload.request;

import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginRequest {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginRequest.class);
	
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	public String getUsername() {
		logger.debug("getUsername enter");
		return username;
	}

	public void setUsername(String username) {
		logger.debug("setUsername enter");
		this.username = username;
	}

	public String getPassword() {
		logger.debug("getPassword enter");
		return password;
	}

	public void setPassword(String password) {
		logger.debug("setPassword enter");
		this.password = password;
	}

}
