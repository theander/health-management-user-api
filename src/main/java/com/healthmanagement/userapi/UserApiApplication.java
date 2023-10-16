package com.healthmanagement.userapi;

import com.healthmanagement.userapi.model.Role;
import com.healthmanagement.userapi.model.UserApp;
import com.healthmanagement.userapi.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.ArrayList;

@SpringBootApplication
public class UserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> {
//            userService.saveRole(new Role(null, "ROLE_USER"));
//            userService.saveRole(new Role(null, "ROLE_LAB"));
//            userService.saveRole(new Role(null, "ROLE_MEDICAL"));
//            userService.saveRole(new Role(null, "ROLE_ADMIN"));
//
//            userService.saveUser(new UserApp(null, "Anderson", "ander", "1234", new ArrayList<>()));
//            userService.saveUser(new UserApp(null, "Anderson1", "ander1", "1234", new ArrayList<>()));
//            userService.saveUser(new UserApp(null, "Anderson2", "ander2", "1234", new ArrayList<>()));
//            userService.saveUser(new UserApp(null, "Anderson3", "ander3", "1234", new ArrayList<>()));
//
//            userService.addRoleToUser("ander", "ROLE_USER");
//            userService.addRoleToUser("ander1", "ROLE_LAB");
//            userService.addRoleToUser("ander2", "ROLE_MEDICAL");
//            userService.addRoleToUser("ander3", "ROLE_ADMIN");
//        };
//    }
}
