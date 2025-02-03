package com.learn.jwt.security.repo;

import com.learn.jwt.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequestMapping
public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
