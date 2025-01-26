package io.github.tuanpq.javafileio.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.tuanpq.javafileio.common.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
