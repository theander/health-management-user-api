package com.healthmanagement.userapi.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmanagement.userapi.model.Role;
import com.healthmanagement.userapi.model.RoleToUserForm;
import com.healthmanagement.userapi.model.UserApp;
import com.healthmanagement.userapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserApp> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok()
                .body(userService.getUserById(userId));
    }

    @GetMapping("/users/count")
    public ResponseEntity<Map<Integer, Integer>> countUsers() {
        final var map = userService.countUsersByMonth();
        return ResponseEntity.ok()
                .body(map);
    }

    @GetMapping("/user-by-email/{email}")
    public ResponseEntity<UserApp> getUserByEmail(@PathVariable String email) {
        final var user = userService.getUserByEmail(email);

        return user != null ? ResponseEntity.ok()
                .body(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/users-by-role/{userRole}")
    public ResponseEntity<List<UserApp>> getUserByRole(@PathVariable("userRole") String userRole) {
        return ResponseEntity.ok()
                .body(userService.getUserByRole(userRole));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<UserApp> deleteUserById(@PathVariable(value = "userId") Long userId) {
        final var userAppResponseEntity = userService.deleteUserById(userId);
        if (isNull(userAppResponseEntity)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserApp>> getAllUsers() {
        List<UserApp> users = userService.getUsers();
        return ResponseEntity.ok()
                .body(users);
    }
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> users = userService.getRoles();
        return ResponseEntity.ok()
                .body(users);
    }
    @PostMapping("/user/save")
    public ResponseEntity<UserApp> saveUser(@RequestBody UserApp user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/user/save")
                .toUriString());
        user.setRegisterDate(OffsetDateTime.now());
        try {
            return ResponseEntity.created(uri)
                    .body(userService.saveUser(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @PutMapping("/user/update")
    public ResponseEntity<UserApp> updateUser(@RequestBody UserApp user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/user/save")
                .toUriString());
        try {
            return ResponseEntity.created(uri)
                    .body(userService.saveUser(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/role/save")
                .toUriString());
        return ResponseEntity.created(uri)
                .body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity addRoleToUser(@RequestBody RoleToUserForm form) {

        userService.addRoleToUser(form.getUsername(), form.getRolename());
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm)
                        .build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UserApp userApp = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(userApp.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(req.getRequestURL()
                                .toString())
                        .withClaim("roles", userApp.getRoles()
                                .stream()
                                .map(Role::getName)
                                .collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(resp.getOutputStream(), tokens);

            } catch (Exception exception) {
                resp.setHeader("error", exception.getMessage());
                resp.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                resp.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(resp.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token missing");
        }
    }
}
