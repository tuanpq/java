package io.github.tuanpq.java.spring.jwt.payload.response;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtResponse {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtResponse.class);
	
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
		logger.debug("JwtResponse enter");
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

	public String getAccessToken() {
		logger.debug("getAccessToken enter");
		return token;
	}

	public void setAccessToken(String accessToken) {
		logger.debug("setAccessToken enter");
		this.token = accessToken;
	}

	public String getTokenType() {
		logger.debug("getTokenType enter");
		return type;
	}

	public void setTokenType(String tokenType) {
		logger.debug("setTokenType enter");
		this.type = tokenType;
	}

	public Long getId() {
		logger.debug("getId enter");
		return id;
	}

	public void setId(Long id) {
		logger.debug("setId enter");
		this.id = id;
	}

	public String getEmail() {
		logger.debug("getEmail enter");
		return email;
	}

	public void setEmail(String email) {
		logger.debug("setEmail enter");
		this.email = email;
	}

	public String getUsername() {
		logger.debug("getUsername enter");
		return username;
	}

	public void setUsername(String username) {
		logger.debug("setUsername enter");
		this.username = username;
	}

	public List<String> getRoles() {
		logger.debug("getRoles enter");
		return roles;
	}

}
