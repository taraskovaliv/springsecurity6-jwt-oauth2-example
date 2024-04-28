package dev.kovaliv.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
}
