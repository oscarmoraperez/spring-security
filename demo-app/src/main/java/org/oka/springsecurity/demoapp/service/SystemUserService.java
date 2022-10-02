package org.oka.springsecurity.demoapp.service;

import lombok.RequiredArgsConstructor;
import org.oka.springsecurity.demoapp.model.SystemUser;
import org.oka.springsecurity.demoapp.model.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

/**
 * UserDetailsService implementation that backs up the user credentials in a DB.
 * Password encoded via BCryptPasswordEncoder.
 */
@Service
@RequiredArgsConstructor
public class SystemUserService implements UserDetailsService {

    private final SystemUserRepository systemUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SystemUser systemUser = systemUserRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return User.withUsername(systemUser.getEmail())
                .password(systemUser.getPassword())
                .disabled(!systemUser.getEnabled())
                .accountExpired(!systemUser.getNonExpired())
                .accountLocked(!systemUser.getNonLocked())
                .credentialsExpired(!systemUser.getCredentialsNonExpired())
                .authorities(getAuthorities(systemUser))
                .build();
    }

    private Collection<GrantedAuthority> getAuthorities(SystemUser user) {

        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toUpperCase()))
                .collect(toList());
    }

    @Bean
    BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
