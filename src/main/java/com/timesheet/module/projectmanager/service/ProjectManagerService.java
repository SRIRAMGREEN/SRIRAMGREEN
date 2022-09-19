package com.timesheet.module.projectmanager.service;

import com.timesheet.module.SuperAdmin.dto.ProjectManagerDto2;
import com.timesheet.module.projectmanager.entity.ProjectManager;
import com.timesheet.module.projectmanager.entity.dto.ProjectManagerDto;
import com.timesheet.module.registration.entity.Registration;

import java.util.List;
import java.util.Optional;

public interface ProjectManagerService {

    ProjectManagerDto2 addProjectManager(ProjectManager projectManager);

    ProjectManagerDto getProjectManagerDetails(int projectManagerId);

    List<ProjectManagerDto> getAllProjectManagerDetails();
    ProjectManagerDto updateProjectManager(ProjectManager projectManager);


    String deleteProjectManager(int projectManagerId);

    void addEntryToProjectManager(Registration registration);
}
