package com.timesheet.module.project.dto;


import com.timesheet.module.client.dto.ClientDto;
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

    private Date projectEndDate;

    private ClientDto clientDto;


}
