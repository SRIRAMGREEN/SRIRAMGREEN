package com.timesheet.module.projectmanager.entity;

import com.timesheet.module.client.entity.Client;
import com.timesheet.module.project.entity.Project;
import com.timesheet.module.task.entity.Task;
import com.timesheet.module.timesheet.entity.Timesheet;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "projectManager")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectManager {

    @Id
    @Column(name = "manager_id",nullable = false, unique = true)
    public int id;

    @Column(nullable = false, name = "projectManagerName")
    public String projectManagerName;

    @Column
    public boolean projectManagerAddedByAdmin;

    @OneToMany(targetEntity = Project.class, mappedBy = "projectManager", fetch = FetchType.LAZY)
    public List<Project> project;

    @OneToMany(targetEntity = Client.class, mappedBy = "projectManager", fetch = FetchType.EAGER)
    private List<Client> client;

    @OneToMany(targetEntity = Task.class, mappedBy = "projectManager", fetch = FetchType.LAZY)
    public List<Task> task;

    @OneToMany(targetEntity = Timesheet.class, mappedBy = "projectManager", fetch = FetchType.LAZY)
    public List<Timesheet> timesheet;

}
