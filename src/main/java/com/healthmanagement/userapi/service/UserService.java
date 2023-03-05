package com.healthmanagement.userapi.service;

import com.healthmanagement.userapi.model.Role;
import com.healthmanagement.userapi.model.UserApp;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserApp getUserById(Long id);
    UserApp saveUser(UserApp user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    UserApp getUser(String username);

    List<UserApp> getUsers();

    ResponseEntity<UserApp> deleteUserById(Long userId);
}
