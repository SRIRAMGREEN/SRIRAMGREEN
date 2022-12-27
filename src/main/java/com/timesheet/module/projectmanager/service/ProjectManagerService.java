package com.timesheet.module.projectmanager.service;

import com.timesheet.module.SuperAdmin.dto.ProjectManagerDto2;
import com.timesheet.module.projectmanager.entity.ProjectManager;
import com.timesheet.module.projectmanager.dto.ProjectManagerDto;
import com.timesheet.module.registration.entity.Registration;

import java.util.List;

public interface ProjectManagerService {

    ProjectManagerDto2 addProjectManager(ProjectManager projectManager);

    ProjectManagerDto getProjectManagerDetails(int projectManagerId);

    List<ProjectManagerDto> getAllProjectManagerDetails();

    ProjectManagerDto updateProjectManager(ProjectManager projectManager);


    void deleteProjectManager(int projectManagerId);

    void addEntryToProjectManager(Registration registration);
}
