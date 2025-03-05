package io.github.tuanpq.java.spring.jwt.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.tuanpq.java.spring.jwt.model.User;

public class UserDetailsImpl implements UserDetails {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsImpl.class);
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String email;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		logger.debug("UserDetailsImpl enter");
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(User user) {
		logger.debug("UserDetailsImpl enter");
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		logger.debug("getAuthorities enter");
		return authorities;
	}

	public Long getId() {
		logger.debug("getId enter");
		return id;
	}

	public String getEmail() {
		logger.debug("getEmail enter");
		return email;
	}

	@Override
	public String getPassword() {
		logger.debug("getPassword enter");
		return password;
	}

	@Override
	public String getUsername() {
		logger.debug("getUsername enter");
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		logger.debug("isAccountNonExpired enter");
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		logger.debug("isAccountNonLocked enter");
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		logger.debug("isCredentialsNonExpired enter");
		return true;
	}

	@Override
	public boolean isEnabled() {
		logger.debug("isEnabled enter");
		return true;
	}

	@Override
	public boolean equals(Object o) {
		logger.debug("equals enter");
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

}
