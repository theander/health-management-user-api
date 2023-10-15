package com.healthmanagement.userapi.repository;

import com.healthmanagement.userapi.model.Role;
import com.healthmanagement.userapi.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserApp,Long> {

    UserApp findUserAppByUsername(String name);
    UserApp findUserAppById(Long id);
    List<UserApp> findUserAppByRolesContaining(Role roles);
    Optional<UserApp> findUserAppByEmail(String email);

    @Query("select count(*) from UserApp r where month(r.registerDate) = :month")
    int countForMonth(int month);

    @Override
    void deleteById(Long aLong);
}
