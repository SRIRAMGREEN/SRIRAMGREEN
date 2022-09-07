//package com.timesheet.entity.model;
//
//import com.timesheet.entity.utils.DateTime;
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "timesheet")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class TimeSheet extends DateTime {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public int taskId;
//
//    @Column(name = "task_name")
//    public String taskName;
//
//    @Column(name = "time_logs")
//    public String timeLogs;
//
//    @Column(name = "total")
//    public String totalNoOfDays;
//
//    @Column(name = "totalNoOfHours")
//    public String totalNoOfHours;
//
//    @Column(name = "task_status")
//    public String status;
//
//    @Column(name = "task_effort")
//    public String effort;
//
//    @Column(name = "teams")
//    public String teams;
//
//    @Column(name = "task_description")
//    public String taskDescription;
//
//    @Column(name = "members_allocation")
//    public String membersAllocated;
//
//    @OneToMany(cascade=CascadeType.ALL, targetEntity=Project.class)
//    @JoinColumn(name = "project_id")
//    public List<Project> project;
//
//}
