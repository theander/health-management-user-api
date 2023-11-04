package com.healthmanagement.userapi.service;

import com.healthmanagement.userapi.model.Role;
import com.healthmanagement.userapi.model.UserApp;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserApp getUserById(Long id);
    UserApp getUserByEmail(String id);

    List<UserApp> getUserByRole(String userRole);

    UserApp saveUser(UserApp user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    UserApp getUser(String username);

    List<UserApp> getUsers();
    List<Role> getRoles();

    UserApp deleteUserById(Long userId);

    Map<Integer, Integer> countUsersByMonth();
}
