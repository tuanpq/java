package io.github.tuanpq.javafileio.common.service;

import java.util.List;

import io.github.tuanpq.javafileio.common.entity.User;

public interface UserService {
    void saveUser(User user);
    User findByEmail(String email);
    List<User> findAllUsers();
}
