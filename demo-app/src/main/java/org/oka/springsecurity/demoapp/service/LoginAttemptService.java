package org.oka.springsecurity.demoapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oka.springsecurity.demoapp.model.SystemUser;
import org.oka.springsecurity.demoapp.model.SystemUserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.time.LocalDateTime.now;

/**
 * Provides business layer features on the login functionality.
 */
@Service
@AllArgsConstructor
@Slf4j
public class LoginAttemptService {

    SystemUserRepository systemUserRepository;

    private final int MAX_ATTEMPT = 3;

    /**
     * It increases the counter of number of login times, last attempted login date.
     * If the counter reaches MAX_ATTEMPT, the user is blocked in the system.
     *
     * @param username
     */
    public void loginFailed(String username) {
        SystemUser systemUser = systemUserRepository.findByEmail(username).orElseThrow();
        systemUser.setNumberOfFailedLoggin(systemUser.getNumberOfFailedLoggin() + 1);
        systemUser.setLastLoginAttemp(now());
        if (systemUser.getNumberOfFailedLoggin() >= MAX_ATTEMPT) {
            systemUser.setNonLocked(false);
        }

        systemUserRepository.saveAndFlush(systemUser);
    }

    /**
     * Recurrent method (via Spring Scheduling) that unlocks users that the last login attempt is before one minute.
     */
    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void unlockUsers() {
        log.info("Init the process of unlocking users ...");
        List<SystemUser> listOfLockedUsers = systemUserRepository.findByNonLocked(false);

        for (SystemUser user : listOfLockedUsers) {
            if (user.getLastLoginAttemp().isBefore(now().minusMinutes(1))) {
                log.info("Unlocking user: " + user);
                user.setLastLoginAttemp(null);
                user.setNumberOfFailedLoggin(0);
                user.setNonLocked(true);
                systemUserRepository.saveAndFlush(user);
                log.info("Done! user: " + user + " unlocked!");
            }
        }
    }
}