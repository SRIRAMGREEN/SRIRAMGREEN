package com.timesheet.module.Employee.entity;

import com.timesheet.module.project.entity.Project;
import com.timesheet.module.task.entity.Task;
import com.timesheet.module.team.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


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

    @OneToMany(targetEntity = Task.class, mappedBy = "employee", fetch = FetchType.EAGER)
    public List<Task> task;


}
