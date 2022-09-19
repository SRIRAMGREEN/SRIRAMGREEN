package com.timesheet.module.registration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "registration")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(nullable = false, name = "employeeName")
    public String employeeName;

    @Column(nullable = false, unique = true, name = "email")
    public String emailId;

    @Column(nullable = false, name = "loginId")
    public String loginId;

    @Column(name = "password")
    public String password;

    @Column(nullable = false, name = "department")
    public String department;

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "mail_status")
    private String mailStatus;

    @Column(name = "status")
    private boolean accountStatus = false;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    @JsonIgnoreProperties("roles")
    @JoinColumn(name = "role_id", referencedColumnName = "roles_id", updatable = false)
    private Roles roles;

}


