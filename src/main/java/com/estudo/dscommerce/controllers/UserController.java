package com.estudo.dscommerce.controllers;

import com.estudo.dscommerce.dto.response.UserResponseDTO;
import com.estudo.dscommerce.services.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/me")
    @PreAuthorize("hasRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    public ResponseEntity<UserResponseDTO> getMe(){
        UserResponseDTO userResponseDTO = userService.getMe();
        return ResponseEntity.ok().body(userResponseDTO);
    }
}
