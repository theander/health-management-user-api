package com.healthmanagement.userapi.repository;

import com.healthmanagement.userapi.model.Role;
import com.healthmanagement.userapi.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<UserApp,Long> {

    UserApp findUserAppByUsername(String name);
    UserApp findUserAppById(Long id);
    List<UserApp> findUserAppByRolesContaining(Role roles);

    @Override
    void deleteById(Long aLong);
}
