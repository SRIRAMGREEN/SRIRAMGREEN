package com.timesheet.module.project.controller;

import com.timesheet.module.project.dto.ProjectDto;
import com.timesheet.module.project.entity.Project;
import com.timesheet.module.project.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @PostMapping(value = "/insertProject")
    public ResponseEntity<ProjectDto> insertProject(@RequestBody Project project) {
        logger.info("ProjectController || insertProjectDetails || Inserting the ProjectDetails Info {} // ->", project);
        return new ResponseEntity<>(projectService.insertProject(project), HttpStatus.OK);
    }

    @GetMapping(value = "/getProjectDetailsByClientId")
    public ResponseEntity<List<ProjectDto>> getProjectDetails(@RequestParam int clientId) {
        logger.info("ProjectController || getProjectDetailsByClientId || getting the ProjectDetails by clientId {} ->", clientId);
        return new ResponseEntity<>(projectService.getProjectDetailsByClientId(clientId), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllProjectDetails")
    public ResponseEntity<List<ProjectDto>> getAllProjectDetails() {
        logger.info("ProjectController || getAllProjectDetails || Getting the ProjectDetails Details from the project");
        return new ResponseEntity<>(projectService.getAllProjectDetails(), HttpStatus.OK);
    }

    @PutMapping(value = "/updateProject")
    public ResponseEntity<ProjectDto> updateProject(@RequestBody Project projects) {
        logger.info("ProjectController || updateProjectDetails || Updating the ProjectDetails Info {} //->", projects);
        return new ResponseEntity<>(projectService.updateProject(projects), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteProjectData")
    public String deleteProject(@RequestParam int projectId) {
        logger.info("ProjectController || deleteProjectDetails || Deleting the ProjectDetails {} // ->", projectId);
        projectService.deleteProject(projectId);
        return "Project Details Deleted Successfully";
    }

    @GetMapping(value = "/getProjectByProjectManagerId")
    public ResponseEntity<List<ProjectDto>> getProjectByProjectManagerId(@RequestParam int id) {
        logger.info("ProjectController || getProjectDetailsByProjectManagerId || getting the ProjectDetails by ProjectManagerId {} ->", id);
        return new ResponseEntity<>(projectService.getProjectByProjectManagerId(id), HttpStatus.OK);
    }

    @PutMapping(value = "/insertImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProjectDto> insertImage(@RequestParam("image") Optional<MultipartFile> image, @RequestParam("id") int projectId) throws IOException {
        logger.info("ProjectController || insertImage || Inserting Images");
        return  new ResponseEntity<>(projectService.insertImage(image, projectId), HttpStatus.OK);
    }

}
