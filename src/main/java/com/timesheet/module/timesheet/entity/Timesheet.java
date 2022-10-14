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

    @Column(name = "periodStartDate")
    public LocalDateTime timesheetStartDate;

    @Column(name = "periodEndDate")
    public LocalDateTime timesheetEndDate;

    @Column(name = "totalHours")
    public Long totalHours;
    @Column(name = "isTimeLimitExceeded")
    public boolean isTimeLimitExceeded = false;
    @Column(name = "timesheetStatus")
    public Boolean timesheetStatus;
    @Column(name = "project_manager_id")
    private int managerId;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    private Task task;

    @OneToMany(targetEntity = TimesheetLogs.class, mappedBy = "timesheet", fetch = FetchType.LAZY)
    private List<TimesheetLogs> timesheetLogsList;

}


