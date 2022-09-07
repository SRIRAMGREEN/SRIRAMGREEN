package com.timesheet.entity.model.dto;


import com.timesheet.entity.utils.DateTime;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;

@Getter
@Setter
public class ProjectDto extends DateTime {

    public int projectId;

    public String projectName;

    public String projectStatus;

    public String projectManager;

    private byte[] image;

}
