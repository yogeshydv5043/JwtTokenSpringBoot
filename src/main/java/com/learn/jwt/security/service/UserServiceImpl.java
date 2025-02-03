package com.learn.jwt.security.service;

import com.learn.jwt.security.dto.RoleRequest;
import com.learn.jwt.security.dto.UserRequestDto;
import com.learn.jwt.security.dto.UserResponseDto;
import com.learn.jwt.security.exception.ResourceNotFoundException;
import com.learn.jwt.security.model.Role;
import com.learn.jwt.security.model.User;
import com.learn.jwt.security.repo.RoleRepo;
import com.learn.jwt.security.repo.UserRepo;
import com.learn.jwt.security.utils.RoleConstants;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    //Create User
    public UserResponseDto createUser(UserRequestDto request) {
        // Check if USER role exists, if not, create it
        Role role = roleRepo.findByName(RoleConstants.getRoleUser()).orElseGet(() -> {
            // If role not found, create it
            roleService.createUserRole(); // Create USER role
            return roleRepo.findByName(RoleConstants.getRoleUser()).orElseThrow(() -> new ResourceNotFoundException("Failed to create user role !!"));
        });
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        //Role add UserRole
        user.getRoles().add(role);
        User saveUser = userRepo.save(user);
        return modelMapper.map(saveUser, UserResponseDto.class);
    }

    //Create Admin
    public UserResponseDto createAdmin(UserRequestDto request) {
        Role role = roleRepo.findByName(RoleConstants.getRoleAdmin()).orElseGet(() -> {
            roleService.createAdminRole();
            return roleRepo.findByName(RoleConstants.getRoleAdmin()).orElseThrow(() -> new ResourceNotFoundException("Failed to create admin role !!"));
        });
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.getRoles().add(role);
        User saveUser = userRepo.save(user);
        return modelMapper.map(saveUser, UserResponseDto.class);
    }

    // Create SuperAdmin
    public UserResponseDto createSuperAdmin(UserRequestDto request) {
        // Check if USER role exists, if not, create it
        Role role = roleRepo.findByName(RoleConstants.getRoleSuperAdmin()).orElseGet(() -> {
            // If role not found, create it
            roleService.createSuperAdminRole(); // Create SuperAdmin role
            return roleRepo.findByName(RoleConstants.getRoleSuperAdmin()).orElseThrow(() -> new ResourceNotFoundException("Failed to create super_admin role !!"));
        });
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        //Role add UserRole
        user.getRoles().add(role);
        User saveUser = userRepo.save(user);
        return modelMapper.map(saveUser, UserResponseDto.class);
    }


    //Get UserByEmail
    public UserResponseDto getByEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found !!"));
        return modelMapper.map(user, UserResponseDto.class);
    }

    //Get UserById
    public UserResponseDto getById(Long Id) {
        User user = userRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("user not found !!"));
        return modelMapper.map(user, UserResponseDto.class);
    }


    //updateUser
    public UserResponseDto updateUser(Long Id, UserRequestDto request) {
        User user = userRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("user not found !!"));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        User saveUser = userRepo.save(user);
        return modelMapper.map(saveUser, UserResponseDto.class);
    }

    public UserResponseDto updateRole(Long Id, RoleRequest roleRequest) {
        User user = userRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("user not found !!"));
        // Fetch the new roles by their names
        String names = roleRequest.getName().toUpperCase();
        Role role = roleRepo.findByName(names).orElseThrow(() -> new ResourceNotFoundException("role not found !!"));
        user.getRoles().clear();
        user.getRoles().add(role);
        User saved = userRepo.save(user);
        return modelMapper.map(saved, UserResponseDto.class);
    }

    @Transactional
    public List<UserResponseDto> getAll() {
        return userRepo.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    public void deleteUser(Long Id) {
        User user = userRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("user not found !!"));
        userRepo.delete(user);
    }
}
