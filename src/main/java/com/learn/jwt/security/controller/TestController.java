package com.learn.jwt.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @Secured("ADMIN")
    @GetMapping("/admin")
    public String getAdminTest() {
        return "Hello world this is admin !!";
    }

    @Secured("SUPER_ADMIN")
    @GetMapping("/super")
    public String getSuperAdminTest() {
        return "Hello world this is super admin !!";
    }


    @PreAuthorize("hasAuthority('SUPER_ADMIN','USER','ADMIN')")
    @GetMapping("/")
    public String getTest() {
        return "Hello world !!";
    }
}
