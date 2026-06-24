package com.ajudabem.api.controllers;

import com.ajudabem.api.domains.user.User;
import com.ajudabem.api.dto.LoginRequestDTO;
import com.ajudabem.api.dto.RegisterRequestDTO;
import com.ajudabem.api.dto.ResponseDTO;
import com.ajudabem.api.repositories.UserRepository;
import com.ajudabem.api.services.security.TokenService;
import com.ajudabem.api.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
            return ResponseEntity.ok(userService.login(body));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        return ResponseEntity.ok(userService.register(body));
    }
}
