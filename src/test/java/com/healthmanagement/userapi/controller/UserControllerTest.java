package com.healthmanagement.userapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmanagement.userapi.model.Role;
import com.healthmanagement.userapi.model.RoleToUserForm;
import com.healthmanagement.userapi.model.UserApp;
import com.healthmanagement.userapi.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc webMvcTest;
    @MockBean
    private UserServiceImpl userService;

    @Test
    void getUserById() throws Exception {
        UserApp userApp = UserApp.builder().id(123l).name("jose").roles(Arrays.asList(new Role(1234l, "ADMIN"))).build();
        when(userService.getUserById(any())).thenReturn(userApp);

        webMvcTest.perform(get("/api/users/{id}", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(123l))
                .andReturn();
    }

    @Test
    void getUserByRole() throws Exception {
        UserApp userApp = UserApp.builder().id(123l).name("jose").roles(Arrays.asList(new Role(1234l, "ADMIN"))).build();
        when(userService.getUserByRole(any())).thenReturn(Arrays.asList(userApp));

        webMvcTest.perform(get("/api/users-by-role/{userRole}", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.size()").value(1))
                .andReturn();

    }

    @Test
    void deleteUserById() throws Exception {
        UserApp userApp = UserApp.builder().id(123l).name("jose").roles(Arrays.asList(new Role(1234l, "ADMIN"))).build();
        when(userService.deleteUserById(any())).thenReturn(new ResponseEntity<UserApp>(userApp, HttpStatus.NO_CONTENT));

        webMvcTest.perform(delete("/api/users/{userId}", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.id").value(123l))
                .andReturn();
    }

    @Test
    void getAllUsers() throws Exception {
        List<UserApp> userApp = Arrays.asList(UserApp.builder().id(123l).name("jose").roles(Arrays.asList(new Role(1234l, "ADMIN"))).build());
        when(userService.getUsers()).thenReturn(new ResponseEntity<List<UserApp>>(userApp, HttpStatus.OK).getBody());

        webMvcTest.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$.[0].id").value(123l))
                .andReturn();
    }

    @Test
    void saveUser() throws Exception {

        var userApp = UserApp.builder().id(123l).name("jose").roles(Arrays.asList(new Role(1234l, "ADMIN"))).build();
        var userToSave = UserApp.builder().name("jose").roles(Arrays.asList(new Role(1234l, "ADMIN"))).build();
        var content = objectMapper.writeValueAsString(userToSave);
        when(userService.saveUser(any())).thenReturn(userApp);



        webMvcTest.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL)
                        .content(content))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(123l))
                .andReturn();
    }

    @Test
    void testSaveRole() throws Exception {

        var role = Role.builder().id(123l).name("ADMIN").build();
        var roleToSave = Role.builder().name("ADMIN").build();
        var content = objectMapper.writeValueAsString(roleToSave);
        when(userService.saveRole(any())).thenReturn(role);

        webMvcTest.perform(post("/api/role/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL)
                        .content(content))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(123l))
                .andExpect(jsonPath("$.name").value("ADMIN"))
                .andReturn();
    }

    @Test
    void addRoleToUser() throws Exception {
        String roleToUser = objectMapper.writeValueAsString(RoleToUserForm.builder().rolename("ADMIN").username("jose").build());

        doNothing().when(userService).addRoleToUser(any(),any());

        webMvcTest.perform(post("/api/role/addtouser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL)
                                .content(roleToUser)
                        )

                .andExpect(status().isOk())
                .andReturn();

    verify(userService,times(1)).addRoleToUser(any(),any());
    }
}