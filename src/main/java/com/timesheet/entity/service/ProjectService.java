package com.timesheet.entity.service;

import com.timesheet.entity.model.Project;

import java.util.List;

public interface ProjectService {

    List<Project> insertProject(List<Project> project);

    List<Project> getProjectDetails(int id);

    Project updateProject(Project project);

    String deleteProject(int id);
}
