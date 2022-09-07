package com.timesheet.entity.model;

import com.timesheet.entity.utils.DateTime;
import lombok.*;

import javax.persistence.*;
import java.util.List;



@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends DateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(nullable = false, name = "employeeName")
    public String employeeName;

    @Column(nullable = false, unique = true, name = "email")
    public String email;

    @Column(nullable = false, name = "loginId"  )
    public String loginId;

    @Column(name = "password")
    public String password;

    @Column(nullable = false, name = "department")
    public String department;


}
