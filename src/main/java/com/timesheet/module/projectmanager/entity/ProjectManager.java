package com.timesheet.module.projectmanager.entity;

import com.timesheet.module.client.entity.Client;
import com.timesheet.module.task.entity.Task;
import com.timesheet.module.project.entity.Project;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id",nullable = false, unique = true)
    public int id;

    @Column(nullable = false, name = "projectManagerName")
    public String projectManagerName;

    @Column(nullable = false, unique = true, name = "email")
    public String emailId;

    @Column(nullable = false, name = "loginId", unique = true)
    public String loginId;

    @Column(nullable = false, name = "department")
    public String department;

    @Column
    private boolean projectManagerAddedByAdmin;

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Client.class)
    @JoinColumn(name = "manager_id")
    private List<Client> client;

}
