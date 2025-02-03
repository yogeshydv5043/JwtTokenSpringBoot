package com.learn.jwt.security.controller;

import com.learn.jwt.security.dto.RoleRequest;
import com.learn.jwt.security.dto.UserRequestDto;
import com.learn.jwt.security.dto.UserResponseDto;
import com.learn.jwt.security.service.UserServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/admin")
    public ResponseEntity<UserResponseDto> createAdmin(@RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.createAdmin(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/super")
    public ResponseEntity<UserResponseDto> createSuperAdmin(@RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.createSuperAdmin(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }


    @PreAuthorize("hasAuthority('SUPER_ADMIN','ADMIN')")
    @PutMapping("/{Id}/")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("Id") Long Id, @RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.updateUser(Id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PutMapping("/{Id}/update-role")
    public ResponseEntity<UserResponseDto> updateRole(@PathVariable("Id") Long Id, @RequestBody RoleRequest roleRequest) {
        UserResponseDto responseDto = userService.updateRole(Id, roleRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN','ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/{Id}")
    public ResponseEntity<UserResponseDto> getByID(@PathVariable("Id") Long Id) {
        return ResponseEntity.ok(userService.getById(Id));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deleteById(@PathVariable("Id") Long Id) {
        userService.deleteUser(Id);
        return ResponseEntity.ok().build();
    }


}
