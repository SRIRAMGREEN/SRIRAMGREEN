package com.timesheet.entity.model;


import com.timesheet.entity.utils.DateTime;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "project")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project extends DateTime  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id",unique = true,nullable = false)
    public int projectId;

    @Column(name = "project_name")
    public String projectName;

    @Column(name="project_status")
    public String projectStatus;

    @Column(name = "project_manager")
    public String projectManager;

    @Lob
    private byte[] image;
}
