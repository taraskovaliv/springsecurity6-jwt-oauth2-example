package dev.kovaliv.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    private String email;
    private String password;
}
