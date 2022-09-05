package com.timesheet.entity.model;

import com.timesheet.entity.registration.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "super_admin")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuperAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(nullable = false, name = "employeeName")
    public String adminName;

    @Column(nullable = false, unique = true, name = "email")
    public String email;

    @Column(name = "password")
    public String password;

    @ManyToMany(targetEntity = Project.class)
    @JoinColumn(name = "project_id")
    public List<Project> project;

    @OneToOne
    @JoinColumn(name = "timesheet_id")
    private TimeSheet timeSheet;

    @OneToMany
    @JoinColumn(name = "user_id")
    public List<User> user;
}
