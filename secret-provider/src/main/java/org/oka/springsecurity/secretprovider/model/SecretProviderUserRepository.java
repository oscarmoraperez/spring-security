package org.oka.springsecurity.secretprovider.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository to retrieve SecretProviderUser information.
 */
public interface SecretProviderUserRepository extends JpaRepository<SecretProviderUser, Long> {

    Optional<SecretProviderUser> findByEmail(String email);

    List<SecretProviderUser> findByNonLocked(boolean nonLocked);
}