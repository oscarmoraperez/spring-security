package org.oka.springsecurity.demoapp.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository to retrieve information about users.
 */
public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

    /**
     * Finds user by email field.
     *
     * @param email
     * @return
     */
    Optional<SystemUser> findByEmail(String email);

    /**
     * Finds users by nonBlocked field.
     *
     * @param nonLocked
     * @return
     */
    List<SystemUser> findByNonLocked(boolean nonLocked);
}