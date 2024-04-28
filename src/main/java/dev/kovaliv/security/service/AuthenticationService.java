package dev.kovaliv.security.service;

import dev.kovaliv.security.dto.SignUpRequest;
import dev.kovaliv.security.dto.SignInRequest;
import dev.kovaliv.security.dto.JwtAuthenticationResponse;
import dev.kovaliv.security.entities.User;
import dev.kovaliv.security.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static dev.kovaliv.security.entities.Provider.Password;
import static dev.kovaliv.security.entities.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        Optional<User> existUser = userRepo.findByEmail(request.getEmail());

        if (existUser.isPresent()) {
            throw new IllegalArgumentException("User with current email already exist. Please sign-in.");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(USER)
                .provider(Password)
                .build();
        user = userRepo.save(user);

        String jwt = jwtUtils.generateJwtToken(user.getEmail());
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        if (!Password.equals(user.getProvider())) {
            throw new IllegalArgumentException("Please use another sign-in method: " + user.getProvider().name());
        }

        if (!user.getPassword().equals(passwordEncoder.encode(request.getPassword()))) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        String jwt = jwtUtils.generateJwtToken(user.getEmail());
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse authenticateGoogleUser(OidcUser oidcUser) {
        if (oidcUser != null) {
            String jwt = jwtUtils.generateJwtToken(oidcUser.getEmail());
            return JwtAuthenticationResponse.builder().token(jwt).build();
        }

        return null;
    }
}
