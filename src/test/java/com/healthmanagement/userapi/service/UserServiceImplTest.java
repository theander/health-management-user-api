package com.healthmanagement.userapi.service;

import com.healthmanagement.userapi.model.Role;
import com.healthmanagement.userapi.model.UserApp;
import com.healthmanagement.userapi.repository.RoleRepository;
import com.healthmanagement.userapi.repository.UserRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername() {
        UserApp userApp = this.dummyUserApp();
        when(userRepository.findUserAppByUsername(any())).thenReturn(userApp);
        UserDetails userDetails = userService.loadUserByUsername("joao");
        assertThat(userDetails).isNotNull();
    }

    @Test
    void getExceptionLoadUserByUsername() {

        when(userRepository.findUserAppByUsername(any())).thenReturn(null);
        Throwable exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(null);
        });
        assertEquals("User not found", exception.getMessage());
    }


    @Test
    void getUserById() {
        UserApp userApp = this.dummyUserApp();
        when(userRepository.findUserAppById(any())).thenReturn(userApp);
        UserApp userById = userService.getUserById(1l);
        assertThat(userById).isNotNull();
    }

    @Test
    void getUserByRole() {
        UserApp userApp = this.dummyUserApp();
        Role role = this.dummyRole();
        when(roleRepository.findRoleByName(any())).thenReturn(role);
        when(userRepository.findUserAppByRolesContaining(any())).thenReturn(Arrays.asList(userApp));
        List<UserApp> userById = userService.getUserByRole("ADMIN");
        assertThat(userById).isNotNull();
        assertThat(userById.size()).isGreaterThan(0);
    }

    @Test
    void saveUser() {
        UserApp userApp = this.dummyUserApp();
        UserApp userApp1 = this.dummyUserApp();
        userApp1.setId(123l);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(userApp1);
        UserApp user = userService.saveUser(userApp);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    void saveRole() {
        Role role = this.dummyRole();
        Role role1 = this.dummyRole();
        role1.setId(123l);

        when(roleRepository.save(any())).thenReturn(role1);

        Role role2 = userService.saveRole(role);
        assertThat(role2.getId()).isEqualTo(123l);
    }

    @Test
    void addRoleToUser() {
        List<Role> roles = Arrays.asList(new Role(5l,"tests"));
        Role role = this.dummyRole();
        UserApp userApp = this.dummyUserApp();
        userApp.setRoles(roles);
        when(roleRepository.findRoleByName(any())).thenReturn(role);
        when(userRepository.findUserAppByUsername(any())).thenReturn(userApp);

        assertThrows(Exception.class,()->{userService.addRoleToUser("joao", "ADMIN");});

        verify(roleRepository, times(1)).findRoleByName(any());
        verify(userRepository, times(1)).findUserAppByUsername(any());
    }

    @Test
    void getUsers() {
        UserApp userApp = this.dummyUserApp();
        when(userRepository.findAll()).thenReturn(Arrays.asList(userApp));
        List<UserApp> users = userService.getUsers();
        assertThat(users).isNotNull();
        assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    void deleteUserById() {
        UserApp userApp = this.dummyUserApp();
        when(userRepository.findById(any())).thenReturn(Optional.of(userApp));

        ResponseEntity<UserApp> userAppResponseEntity = userService.deleteUserById(2l);
        assertThat(userAppResponseEntity).isNotNull();
        assertEquals(userAppResponseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
        verify(userRepository, times(1)).deleteById(any());
    }

    @Test
    void GetNotFoundOnDeleteUserById() {
        UserApp userApp = this.dummyUserApp();
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        ResponseEntity responseEntity = userService.deleteUserById(2l);


        assertThat(responseEntity.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo(2L);

        verify(userRepository, times(0)).deleteById(any());
    }

    private UserApp dummyUserApp() {
        return UserApp.builder().name("joao").username("joaozinho").password("password").roles(Arrays.asList(new Role(5l, "ADMIN"))).build();
    }

    private Role dummyRole() {
        return Role.builder().id(3l).name("ADMIN").build();
    }
}