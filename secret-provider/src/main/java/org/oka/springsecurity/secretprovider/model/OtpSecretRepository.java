package org.oka.springsecurity.secretprovider.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository to retrieve OtpSecret information.
 */
public interface OtpSecretRepository extends JpaRepository<OtpSecret, Long> {
    boolean existsByCode(final String code);

    int deleteByCode(final String code);

    List<OtpSecret> findByCreatedDateBefore(final LocalDateTime dateTime);
}

