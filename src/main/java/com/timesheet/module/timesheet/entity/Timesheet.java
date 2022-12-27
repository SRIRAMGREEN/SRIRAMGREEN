package com.timesheet.module.timesheet.entity;

import com.timesheet.module.Employee.entity.Employee;
import com.timesheet.module.task.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "timesheet")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timesheet_Id", unique = true)
    public int id;

    @Column(name = "totalHours")
    public Long totalHours;

    @Column(name = "project_manager_id")
    private int managerId;

    @Column(name = "timesheetStatus")
    public Boolean timesheetStatus = false;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "employee_id",referencedColumnName = "employee_id")
    private Employee employee;

    @OneToMany(targetEntity = Task.class, mappedBy = "timesheet",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Task> task;

    @OneToMany(targetEntity = TimesheetLogs.class, mappedBy = "timesheet",fetch = FetchType.LAZY)
    private List<TimesheetLogs> timesheetLogsList;

}


