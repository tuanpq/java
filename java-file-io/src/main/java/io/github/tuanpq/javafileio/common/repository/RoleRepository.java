package io.github.tuanpq.javafileio.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.tuanpq.javafileio.common.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
