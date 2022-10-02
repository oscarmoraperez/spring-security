package org.oka.springsecurity.secretprovider.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Represents a OTP Secret model.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpSecret {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private LocalDateTime createdDate;
}

