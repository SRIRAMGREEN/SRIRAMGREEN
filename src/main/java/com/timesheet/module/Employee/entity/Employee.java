package com.timesheet.module.Employee.entity;

import com.timesheet.module.client.entity.Client;
import com.timesheet.module.team.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @Column(name = "employee_id", nullable = false, unique = true)
    public int id;

    @Column(nullable = false, name = "employeeName")
    public String employeesName;

    @Column
    private boolean employeeAddedByAdmin;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    public Team team;

    @ManyToOne(fetch = FetchType.EAGER,targetEntity = Employee.class)
    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    public Employee employee;


}
