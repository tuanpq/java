package io.github.tuanpq.java.spring.jwt.model;

import javax.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "roles")
public class Role {
	
	private static final Logger logger = LoggerFactory.getLogger(Role.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RoleEnumeration name;

	public Role() {
		logger.debug("Role enter");
	}

	public Role(RoleEnumeration name) {
		logger.debug("Role enter");
		this.name = name;
	}

	public Integer getId() {
		logger.debug("getId enter");
		return id;
	}

	public void setId(Integer id) {
		logger.debug("setId enter");
		this.id = id;
	}

	public RoleEnumeration getName() {
		logger.debug("getName enter");
		return name;
	}

	public void setName(RoleEnumeration name) {
		logger.debug("setName enter");
		this.name = name;
	}
}