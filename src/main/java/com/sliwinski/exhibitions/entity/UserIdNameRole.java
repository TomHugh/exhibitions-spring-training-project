package com.sliwinski.exhibitions.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="USERS")
public class UserIdNameRole {
    @Id
    @Column(name = "USER_ID")
    private long id;
    @Column(name = "USERNAME")
    private String username;
    @Column(name="ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;
}