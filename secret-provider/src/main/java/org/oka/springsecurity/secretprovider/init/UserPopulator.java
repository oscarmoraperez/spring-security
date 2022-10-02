package org.oka.springsecurity.secretprovider.init;

import lombok.RequiredArgsConstructor;
import org.oka.springsecurity.secretprovider.model.Role;
import org.oka.springsecurity.secretprovider.model.SecretProviderUser;
import org.oka.springsecurity.secretprovider.model.SecretProviderUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Populates 1 users with the required authority 'STANDARD' to use this application: secret-provider.
 */
@Component
@RequiredArgsConstructor
public class UserPopulator {
    private final SecretProviderUserRepository systemUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void populate() {
        SecretProviderUser user0 = SecretProviderUser.builder()
                .email("admin")
                .password(passwordEncoder.encode("pass"))
                .nonExpired(true)
                .nonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .roles(List.of(Role.builder().name("STANDARD").build()))
                .build();

        systemUserRepository.saveAll(List.of(user0));
    }
}
