package com.lucas.credentials.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table
public class UserDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    @Column
    @JsonIgnore
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "credential_status")
    private CredentialStatus status;

    public UserDao() {

    }

}
