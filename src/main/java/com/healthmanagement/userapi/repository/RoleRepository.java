package com.healthmanagement.userapi.repository;

import com.healthmanagement.userapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);

}
