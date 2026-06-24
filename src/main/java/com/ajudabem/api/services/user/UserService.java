package com.ajudabem.api.services.user;

import com.ajudabem.api.domains.user.User;
import com.ajudabem.api.domains.user.UserRole;
import com.ajudabem.api.dto.LoginRequestDTO;
import com.ajudabem.api.dto.RegisterRequestDTO;
import com.ajudabem.api.dto.ResponseDTO;
import com.ajudabem.api.dto.UserResponseDTO;
import com.ajudabem.api.repositories.UserRepository;
import com.ajudabem.api.services.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public ResponseDTO register(RegisterRequestDTO dto) {

        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        User newUser = new User();

        newUser.setPassword(passwordEncoder.encode(dto.password()));
        newUser.setEmail(dto.email());
        newUser.setPhone(dto.phone());
        newUser.setName(dto.name());
        newUser.setRole(UserRole.USER);
        newUser.setLast_login_at(LocalDateTime.now());
        this.repository.save(newUser);

        String token = this.tokenService.generateToken(newUser);
        return new ResponseDTO(newUser.getName(), token);
    }

    public ResponseDTO login(LoginRequestDTO dto) {

        User user = this.repository.findByEmail(dto.email()).orElseThrow(() ->
                new RuntimeException(("User not found")));

        if(passwordEncoder.matches(dto.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            user.setLast_login_at(LocalDateTime.now());
            this.repository.save(user);
            return new ResponseDTO(user.getName(), token);
        }

        return null;
    }

    public UserResponseDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(auth.getName());

        User user = repository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getProfile_image(),
                user.getCpf(),
                user.getRole(),
                user.getBirth_date()
        );
    }
}
