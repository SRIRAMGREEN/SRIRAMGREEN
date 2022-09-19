package com.timesheet.module.team.entity.dto;

import com.timesheet.module.task.entity.dto.TaskDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDto {

    public int TeamId;

    public String teamName;

    public TaskDto taskDto;

}
