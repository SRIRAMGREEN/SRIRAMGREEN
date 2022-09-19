package com.timesheet.module.task.entity;

import com.timesheet.module.project.entity.Project;
import com.timesheet.module.team.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


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

    @Column(nullable = false, name = "taskStartDate")
    public Date taskStartDate;

    @Column(name = "taskEndDate")
    public Date taskEndDate;

    @Column(nullable = false, name = "taskEffort", unique = true)
    public String taskEffort;

    @Column(name = "percentageOfAllocation")
    public String percentageOfAllocation;

    @Column(name = "taskDescription")
    public String taskDescription;

    @Column(name = "taskStatus")
    public String status;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    public Project project;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Team.class, mappedBy = "task")
    public List<Team> team;
}
