package com.timesheet.module.project.service;

import com.timesheet.module.project.dto.ProjectDto;
import com.timesheet.module.project.entity.Project;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProjectService {

    ProjectDto insertProject(Project project);
    List<ProjectDto> getProjectByProjectManagerId(int id);
    List<ProjectDto> getProjectDetailsByClientId(int clientId);
    List<ProjectDto> getAllProjectDetails();
    ProjectDto updateProject(Project project);

    String deleteProject(int projectId);

    ProjectDto insertImage(Optional<MultipartFile> image, int projectId);

}
