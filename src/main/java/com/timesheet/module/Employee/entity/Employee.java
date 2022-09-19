package com.timesheet.module.Employee.entity;

import com.timesheet.module.utils.DateTime;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends DateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id",nullable = false, unique = true)
    public int id;

    @Column(nullable = false, name = "employeeName")
    public String employeeName;

    @Column(nullable = false, unique = true, name = "email")
    public String emailId;

    @Column(nullable = false, name = "loginId",unique = true)
    public String loginId;

    @Column(nullable = false, name = "department")
    public String department;

    @Column
    private String status;

    @Column
    private boolean employeeAddedByAdmin;


}
