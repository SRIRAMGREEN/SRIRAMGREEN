package com.timesheet.module.project.dto;

import com.timesheet.module.client.entity.Client;
import com.timesheet.module.client.entity.dto.ClientDto;
import com.timesheet.module.task.entity.Task;
import com.timesheet.module.utils.DateTime;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
public class ProjectDto extends DateTime {

    public int projectId;

    public String projectName;

    public String projectStatus;

    public String projectManager;

//    public byte[] image;

    private ClientDto clientDto;
}
