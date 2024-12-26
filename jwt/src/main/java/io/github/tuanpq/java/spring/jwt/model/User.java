package io.github.tuanpq.java.spring.jwt.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class User {
	
	private static final Logger logger = LoggerFactory.getLogger(User.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {
		logger.debug("User enter");
	}

	public User(String username, String email, String password) {
		logger.debug("User enter");
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		logger.debug("getId enter");
		return id;
	}

	public void setId(Long id) {
		logger.debug("setId enter");
		this.id = id;
	}

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

	public Set<Role> getRoles() {
		logger.debug("getRoles enter");
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		logger.debug("setRoles enter");
		this.roles = roles;
	}

}
