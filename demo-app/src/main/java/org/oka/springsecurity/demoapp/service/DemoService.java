package org.oka.springsecurity.demoapp.service;

import lombok.RequiredArgsConstructor;
import org.oka.springsecurity.demoapp.model.SystemUser;
import org.oka.springsecurity.demoapp.model.SystemUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for the demo app features.
 */
@Service
@RequiredArgsConstructor
public class DemoService {
    private final SystemUserRepository systemUserRepository;

    public List<SystemUser> findLockedUsers() {
        List<SystemUser> returnedUsers = new ArrayList<>();
        // Mask the password
        systemUserRepository.findByNonLocked(false).forEach(u -> {
            u.setPassword("*****");
            returnedUsers.add(u);
        });

        return returnedUsers;
    }
}
