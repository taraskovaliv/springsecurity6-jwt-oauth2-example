package dev.kovaliv.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
}
