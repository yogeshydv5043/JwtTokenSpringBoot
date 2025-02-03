package com.learn.jwt.security.service;

import com.learn.jwt.security.exception.ResourceNotFoundException;
import com.learn.jwt.security.model.User;
import com.learn.jwt.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User email not found !! "));

        return new CustomUserDetails(user);
    }
}
