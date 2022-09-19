package com.timesheet.module.project.entity;

import com.timesheet.module.client.entity.Client;
import com.timesheet.module.task.entity.Task;
import com.timesheet.module.utils.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Project")
@Getter
@Setter
public class Project extends DateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", unique = true)
    public int projectId;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    @JoinColumn(name = "client_id",referencedColumnName = "client_id")
    public Client client;

    @Column(name = "project_name")
    public String projectName;

    @Column(name = "client_name")
    public String clientName;

    @Column(name = "project_manager")
    public String projectManagerName;

    @Column(name = "project_status")
    public String projectStatus;

    @Column
    public Date projectStartDate;

    @Column
    public Date projectEndDate;

    @Lob
    private byte[] image;

    @OneToMany(fetch = FetchType.LAZY ,targetEntity = Task.class,mappedBy = "project")
    public List<Task> task;
}
