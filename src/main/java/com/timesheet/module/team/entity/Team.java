package com.timesheet.module.team.entity;

import com.timesheet.module.task.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "team_id", unique = true)
        public int id;

        @Column(nullable = false, name = "teamName")
        public String teamName;

        @ManyToOne(fetch = FetchType.EAGER,targetEntity = Task.class)
        @JoinColumn(name = "task_id",referencedColumnName = "task_id")
        public Task task;
}
