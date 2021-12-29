package com.springsecurity.entity;

import com.springsecurity.enums.Role;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
