package io.github.tuanpq.java.spring.jwt.payload.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageResponse {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageResponse.class);
	
	private String message;

	public MessageResponse(String message) {
		logger.debug("MessageResponse enter");
		this.message = message;
	}

	public String getMessage() {
		logger.debug("getMessage enter");
		return message;
	}

	public void setMessage(String message) {
		logger.debug("setMessage enter");
		this.message = message;
	}

}
