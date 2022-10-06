package com.timesheet.module.projectmanager.entity;

import com.timesheet.module.client.entity.Client;
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

    @OneToMany(targetEntity = Client.class, mappedBy = "projectManager", fetch = FetchType.EAGER)
    private List<Client> client;

}
