package com.timesheet.module.task.entity;

import com.timesheet.module.Employee.entity.Employee;
import com.timesheet.module.client.entity.Client;
import com.timesheet.module.project.entity.Project;
import com.timesheet.module.projectmanager.entity.ProjectManager;
import com.timesheet.module.timesheet.entity.Timesheet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", unique = true)
    public int taskId;

    @Column(nullable = false, name = "taskName")
    public String taskName;

    @Temporal(TemporalType.DATE)
    public Date taskStartDate;

    @Temporal(TemporalType.DATE)
    public Date taskEndDate;

    @Column(nullable = false, name = "taskEffort")
    public String taskEffort;

    @Column(name = "percentageOfAllocation")
    public double percentageOfAllocation;

    @Column(name = "taskDescription")
    public String taskDescription;

    @Column(name = "taskStatus")
    public String status;

//    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
//    @JoinColumn(name = "employee_id",referencedColumnName = "employee_id")
//    public Employee employee;

    @ManyToOne(fetch = FetchType.EAGER,targetEntity = Employee.class)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    public Employee employee;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "timesheet_id", referencedColumnName = "timesheet_id")
    private Timesheet timesheet;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "project_manager_id",referencedColumnName = "manager_id")
    public ProjectManager projectManager;

}
