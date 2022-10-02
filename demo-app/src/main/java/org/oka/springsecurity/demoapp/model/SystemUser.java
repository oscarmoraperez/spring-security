package org.oka.springsecurity.demoapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Model object that represents a user in the system.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    @ToString.Exclude
    private String password;
    @Column(nullable = false)
    private Boolean nonExpired;
    @Column(nullable = false)
    private Boolean nonLocked;
    @Column(nullable = false)
    private Boolean credentialsNonExpired;
    @Column(nullable = false)
    private Boolean enabled;
    @Column
    private LocalDateTime lastLoginAttemp;
    @Column
    private int numberOfFailedLoggin;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Role> roles;

}