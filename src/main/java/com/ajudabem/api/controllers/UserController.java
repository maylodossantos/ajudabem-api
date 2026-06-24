package com.ajudabem.api.controllers;

import com.ajudabem.api.dto.UserResponseDTO;
import com.ajudabem.api.repositories.UserRepository;
import com.ajudabem.api.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    private ResponseEntity<UserResponseDTO> getMe() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }
}
