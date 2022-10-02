package org.oka.springsecurity.secretprovider.service;

import org.oka.springsecurity.secretprovider.model.SecretProviderUser;
import org.oka.springsecurity.secretprovider.model.SecretProviderUserRepository;
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
public class SystemUserService implements UserDetailsService {

    @Autowired
    private SecretProviderUserRepository secretProviderUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SecretProviderUser secretProviderUser = secretProviderUserRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return User.withUsername(secretProviderUser.getEmail())
                .password(secretProviderUser.getPassword())
                .disabled(!secretProviderUser.getEnabled())
                .accountExpired(!secretProviderUser.getNonExpired())
                .accountLocked(!secretProviderUser.getNonLocked())
                .credentialsExpired(!secretProviderUser.getCredentialsNonExpired())
                .authorities(getAuthorities(secretProviderUser))
                .build();
    }

    private Collection<GrantedAuthority> getAuthorities(SecretProviderUser user) {

        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toUpperCase()))
                .collect(toList());
    }

    @Bean
    BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
