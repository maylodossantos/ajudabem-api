package com.ajudabem.api.services.user;

import com.ajudabem.api.domains.user.User;
import com.ajudabem.api.domains.user.UserRole;
import com.ajudabem.api.dto.auth.LoginRequestDTO;
import com.ajudabem.api.dto.auth.RegisterRequestDTO;
import com.ajudabem.api.dto.auth.ResponseDTO;
import com.ajudabem.api.dto.user.UpdateUserRequestDTO;
import com.ajudabem.api.dto.user.UserResponseDTO;
import com.ajudabem.api.repositories.UserRepository;
import com.ajudabem.api.services.security.CurrentUserService;
import com.ajudabem.api.services.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final CurrentUserService currentUserService;

    public ResponseDTO register(RegisterRequestDTO dto) {

        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email is using");
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

        User user = currentUserService.get();

        return UserResponseDTO.fromEntity(user);
    }

    public UserResponseDTO updateCurrentUser(UpdateUserRequestDTO dto) {

        User user = currentUserService.get();

        if (dto.name() != null) {
            user.setName(dto.name());
        }

        if (dto.phone() != null) {
            user.setPhone(dto.phone());
        }

        if (dto.profileImage() != null) {
            user.setProfile_image(dto.profileImage());
        }

        repository.save(user);

        return UserResponseDTO.fromEntity(user);
    }

    public void deleteMe() {
        User user = currentUserService.get();

        if(user.getDeleted()) {
            throw new RuntimeException("User already deleted");
        }

        user.softDelete();
    }

}