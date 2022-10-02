package org.oka.springsecurity.demoapp.controller;

import lombok.RequiredArgsConstructor;
import org.oka.springsecurity.demoapp.model.SystemUser;
import org.oka.springsecurity.demoapp.service.DemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.http.HttpStatus.OK;

/**
 * Simple controller that provides 4 endpoints: /info, /about, /admin and /locked-users.
 */
@RestController
@RequiredArgsConstructor
public class DemoController {
    private final DemoService demoService;

    @GetMapping(value = "/info")
    public ResponseEntity<Map<String, Object>> info() {
        return new ResponseEntity<>(getRandomData(), OK);
    }

    @GetMapping(value = "/about")
    public ResponseEntity<String> about() {
        return new ResponseEntity<>("This is a demo non protected endpoint", OK);
    }

    @GetMapping(value = "/admin")
    public ResponseEntity<String> admin() {
        return new ResponseEntity<>("This is a admin protected endpoint", OK);
    }

    @GetMapping(value = "/locked-users")
    public ResponseEntity<List<SystemUser>> lockedUsers() {
        return new ResponseEntity<>(demoService.findLockedUsers(), OK);
    }

    /**
     * Generates random data to provide as a response
     */
    private Map<String, Object> getRandomData() {
        Map<String, Object> data = new HashMap<>();

        for (int i = 0; i < 15; i++) {
            data.put("field_" + i, randomAlphabetic(10));
        }

        return data;
    }
}
