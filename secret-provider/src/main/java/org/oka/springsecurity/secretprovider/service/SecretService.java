package org.oka.springsecurity.secretprovider.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.oka.springsecurity.secretprovider.model.OtpSecret;
import org.oka.springsecurity.secretprovider.model.OtpSecretRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SecretService {
    private final OtpSecretRepository otpSecretRepository;

    /**
     * It creates and persists an OTP.
     */
    public OtpSecret createOtp() {
        final String code = RandomStringUtils.randomAlphabetic(10);
        OtpSecret optSecret = OtpSecret.builder().code(code).createdDate(LocalDateTime.now()).build();

        return otpSecretRepository.saveAndFlush(optSecret);
    }

    /**
     * Checks if an OTPSecret is valid. If so return true and deletes it from the system. If not, returns false.
     *
     * @param otpCode
     * @return
     */
    public boolean getSecretAccess(String otpCode) {
        if (otpSecretRepository.existsByCode(otpCode)) {
            otpSecretRepository.deleteByCode(otpCode);

            return true;
        }

        return false;
    }

    /**
     * Recurrent job (via Spring Scheduling) that deletes OtpSecret older than 1 minute.
     */
    @Scheduled(fixedDelay = 10000)
    public void cleanOtpSecrets() {
        log.info("Starting the deletion of otp secrets ...");
        List<OtpSecret> otpSecretList = otpSecretRepository.findByCreatedDateBefore(LocalDateTime.now().minusMinutes(1));

        for (OtpSecret otpSecret : otpSecretList) {
            otpSecretRepository.deleteById(otpSecret.getId());
            log.info("Deleting: " + otpSecret);
        }
    }
}
