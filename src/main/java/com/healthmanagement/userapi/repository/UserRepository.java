package com.healthmanagement.userapi.repository;

import com.healthmanagement.userapi.model.UserApp;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserApp,Long> {

    UserApp findUserAppByUsername(String name);
    UserApp findUserAppById(Long id);

    @Override
    void deleteById(Long aLong);
}
