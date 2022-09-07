package com.timesheet.entity.service;

import com.timesheet.entity.model.dto.ProjectDto;
import com.timesheet.entity.model.Project;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ProjectService {

    ProjectDto insertProject(Project project);

    ProjectDto getProjectDetails(int projectId);

    ProjectDto updateProject(Project project);

    String deleteProject(int projectId);

    Boolean insertImage(Optional<MultipartFile> image, int projectId);
}
