package dev.kovaliv.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import dev.kovaliv.security.dto.SignUpRequest;
import dev.kovaliv.security.dto.SignInRequest;
import dev.kovaliv.security.dto.JwtAuthenticationResponse;
import dev.kovaliv.security.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @GetMapping("/loginSuccess")
    public ResponseEntity<JwtAuthenticationResponse> getLoginInfo(@AuthenticationPrincipal OidcUser oidcUser) {
        return ResponseEntity.ok(authenticationService.authenticateGoogleUser(oidcUser));
    }
}
