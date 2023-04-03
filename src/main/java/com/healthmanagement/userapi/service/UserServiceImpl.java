package com.healthmanagement.userapi.service;

import com.healthmanagement.userapi.model.Role;
import com.healthmanagement.userapi.model.UserApp;
import com.healthmanagement.userapi.repository.RoleRepository;
import com.healthmanagement.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserApp user = getUser(username);
        if (user == null) {
            log.error("Username: {} not found", username);
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public UserApp getUserById(Long id) {
        log.info("Get user Id {} on the database", id);
        return userRepository.findUserAppById(id);
    }

    @Override
    public List<UserApp> getUserByRole(String userRole) {
        Role role =roleRepository.findRoleByName(userRole);
        return userRepository.findUserAppByRolesContaining(role);
    }

    @Override
    public UserApp saveUser(UserApp user) {

        log.info("Saving user {} on the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role {} on the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Add role {} to user {}", roleName, username);
        UserApp user = userRepository.findUserAppByUsername(username);

        Role role = roleRepository.findRoleByName(roleName);
        user.getRoles()
                .add(role);
    }

    @Override
    public UserApp getUser(String username) {
        log.info("Get user {} by name", username);
        return userRepository.findUserAppByUsername(username);
    }

    @Override
    public List<UserApp> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<UserApp> deleteUserById(Long userId) {
        log.info("Deleting user by Id:{}", userId);
        Optional<UserApp> userApp = userRepository.findById(userId);
        if (userApp.isPresent()) {

            userRepository.deleteById(userId);
            return new ResponseEntity<UserApp>(userApp.get(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}
