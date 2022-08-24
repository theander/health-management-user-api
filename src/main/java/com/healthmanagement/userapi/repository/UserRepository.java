package com.healthmanagement.userapi.repository;

import com.healthmanagement.userapi.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserApp,Long> {

    UserApp findUserAppByUsername(String name);
    UserApp findUserAppById(Long id);
}
