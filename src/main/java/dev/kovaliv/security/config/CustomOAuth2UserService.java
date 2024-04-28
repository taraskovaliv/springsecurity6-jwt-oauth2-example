package dev.kovaliv.security.config;

import dev.kovaliv.security.entities.Provider;
import dev.kovaliv.security.entities.Role;
import dev.kovaliv.security.entities.User;
import dev.kovaliv.security.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.HashSet;

import static java.lang.System.getenv;

@Component
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final UserRepo userRepo;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = new DefaultOidcUser(
                new HashSet<>(), userRequest.getIdToken(),
                new OidcUserInfo(userRequest.getIdToken().getClaims())
        );

        if (userRepo.findByEmail(oidcUser.getUserInfo().getEmail()).isEmpty()) {
            User user = User.builder()
                    .name(oidcUser.getUserInfo().getFullName())
                    .email(oidcUser.getUserInfo().getEmail())
                    .role(Role.USER)
                    .provider(Provider.Google)
                    .build();
            userRepo.save(user);
        }

        return oidcUser;
    }
}
