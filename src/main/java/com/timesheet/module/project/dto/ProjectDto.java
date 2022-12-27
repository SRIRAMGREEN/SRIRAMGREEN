package com.timesheet.module.project.dto;


import com.timesheet.module.client.dto.ClientDto;
import com.timesheet.module.projectmanager.dto.ProjectManagerDto;
import com.timesheet.module.projectmanager.entity.ProjectManager;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;



@Getter
@Setter
public class ProjectDto{

    private int projectId;

    private String projectName;

    private String projectManager;

    private Date projectStartDate;

    private byte[] image;

    private Date projectEndDate;

    private ClientDto clientDto;

    public ProjectManagerDto projectManagerDto;


}
