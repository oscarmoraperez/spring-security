package org.oka.springsecurity.demoapp.init;

import lombok.RequiredArgsConstructor;
import org.oka.springsecurity.demoapp.model.Role;
import org.oka.springsecurity.demoapp.model.SystemUser;
import org.oka.springsecurity.demoapp.model.SystemUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Populates 3 users with different authorities in the DB when starting the application.
 */
@Component
@RequiredArgsConstructor
public class UserPopulator {
    private final SystemUserRepository systemUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void populate() {
        SystemUser user0 = SystemUser.builder()
                .email("admin")
                .password(passwordEncoder.encode("pass"))
                .nonExpired(true)
                .nonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .roles(List.of(Role.builder().name("VIEW_INFO").build(), Role.builder().name("VIEW_ADMIN").build()))
                .build();

        SystemUser user1 = SystemUser.builder()
                .email("user1")
                .password(passwordEncoder.encode("pass"))
                .nonExpired(true)
                .nonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .roles(List.of(Role.builder().name("VIEW_INFO").build()))
                .build();

        SystemUser user2 = SystemUser.builder()
                .email("user2")
                .password(passwordEncoder.encode("pass"))
                .nonExpired(true)
                .nonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .roles(List.of(Role.builder().name("VIEW_ADMIN").build()))
                .build();

        systemUserRepository.saveAll(List.of(user0, user1, user2));
    }
}
