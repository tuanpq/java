package io.github.tuanpq.java.spring.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.tuanpq.java.spring.jwt.model.Role;
import io.github.tuanpq.java.spring.jwt.model.RoleEnumeration;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(RoleEnumeration name);
	
}
