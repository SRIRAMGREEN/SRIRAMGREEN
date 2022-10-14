package com.timesheet.module.project.entity;

import com.timesheet.module.client.entity.Client;
import com.timesheet.module.projectmanager.entity.ProjectManager;
import com.timesheet.module.task.entity.Task;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Project")
@Getter
@Setter
public class Project  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", unique = true)
    public int projectId;

    @Column(unique = true,name = "project_name")
    public String projectName;

    @Column(name = "project_manager")
    public String projectManagerName;

    @Column(name = "project_status")
    public String projectStatus;

    @Column
    public Date projectStartDate;

    @Column
    public Date projectEndDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "project_manager_id",referencedColumnName = "manager_id")
    public ProjectManager projectManager;

    @ManyToOne(fetch = FetchType.EAGER,targetEntity = Client.class)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    public Client client;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Task.class, mappedBy = "project")
    public List<Task> task;

    @Lob
    private byte[] image;
}
