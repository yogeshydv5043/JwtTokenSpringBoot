package com.learn.jwt.security.service;

import com.learn.jwt.security.model.Role;
import com.learn.jwt.security.repo.RoleRepo;
import com.learn.jwt.security.utils.RoleConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    private RoleRepo roleRepo;

    public void createUserRole() {
        Role role = new Role();
        role.setName(RoleConstants.ROLE_USER);
        Role userRole = roleRepo.save(role);
        log.info("User created : {}",userRole);
    }
    public void createAdminRole() {
        Role role = new Role();
        role.setName(RoleConstants.ROLE_ADMIN);
        Role adminRole = roleRepo.save(role);
        log.info("Admin  created : {}",adminRole);
    }
    public void createSuperAdminRole() {
        Role role = new Role();
        role.setName(RoleConstants.ROLE_SUPER_ADMIN);
        Role superRole = roleRepo.save(role);
        log.info("Super user created : {}",superRole);
    }


}
