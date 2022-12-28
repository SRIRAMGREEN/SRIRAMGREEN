package com.timesheet.module.timesheet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.timesheet.module.Employee.entity.Employee;
import com.timesheet.module.projectmanager.entity.ProjectManager;
import com.timesheet.module.task.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
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

//    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public String timesheetStartDate;

//    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public String timesheetEndDate;

    @Column(name = "totalHours")
    public Long totalHours;

    @Column(name = "isTimeLimitExceeded")
    public boolean isTimeLimitExceeded = false;

    @Column(name = "timesheetStatus")
    public String timesheetStatus;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    private Task task;

    @OneToMany(targetEntity = TimesheetLogs.class, mappedBy = "timesheet", fetch = FetchType.LAZY)
    private List<TimesheetLogs> timesheetLogsList;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "project_manager_id",referencedColumnName = "manager_id")
    public ProjectManager projectManager;

}


