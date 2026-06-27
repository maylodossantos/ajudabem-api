package com.ajudabem.api.services.security;

import com.ajudabem.api.domains.user.User;
import com.ajudabem.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User get() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
