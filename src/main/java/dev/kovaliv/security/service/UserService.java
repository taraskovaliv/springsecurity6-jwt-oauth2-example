package dev.kovaliv.security.service;

import dev.kovaliv.security.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public UserDetailsService userDetailsService() {
        return username -> userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}
