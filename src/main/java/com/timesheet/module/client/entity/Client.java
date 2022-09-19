package com.timesheet.module.client.entity;

import com.timesheet.module.project.entity.Project;
import com.timesheet.module.projectmanager.entity.ProjectManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", unique = true)
    public int clientId;

    @Column(name = "project_name")
    public String clientName;

    @Column(name = "description")
    public String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "manager_id",referencedColumnName = "manager_id")
    public ProjectManager projectManager;

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Project.class,mappedBy = "client")
    private List<Project> project;
}
