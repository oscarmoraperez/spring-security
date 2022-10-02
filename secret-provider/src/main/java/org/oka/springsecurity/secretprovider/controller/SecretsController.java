package org.oka.springsecurity.secretprovider.controller;

import lombok.RequiredArgsConstructor;
import org.oka.springsecurity.secretprovider.model.OtpSecret;
import org.oka.springsecurity.secretprovider.service.SecretService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

/**
 * Simple controller that provides 2 endpoints: /otp (provides one time password), /secret-information (displays secret
 * information if a correct OTP is provided).
 */
@RestController
@RequiredArgsConstructor
public class SecretsController {
    private final SecretService service;

    @GetMapping(value = "/otp")
    public ResponseEntity<Map<String, Object>> otp() {
        OtpSecret otp = service.createOtp();

        return new ResponseEntity<>(Map.of("otp", otp, "_link", "/secret-information/" + otp.getCode()), OK);
    }

    @GetMapping(value = "/secret-information/{otp-code}")
    public ResponseEntity<String> secretInformation(@PathVariable(name = "otp-code") String code) {

        if (service.getSecretAccess(code)) {
            return new ResponseEntity<>("Very secret information available only via OTP", OK);
        }

        return new ResponseEntity<>("OTP Code not valid!", FORBIDDEN);
    }
}
