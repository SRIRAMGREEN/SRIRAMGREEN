package com.timesheet.entity.controller;

import com.timesheet.entity.model.dto.ProjectDto;
import com.timesheet.entity.model.Project;
import com.timesheet.entity.repository.ProjectRepo;
import com.timesheet.entity.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRepo projectRepo;

    Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @PostMapping(value = "/insertProject")
    public ResponseEntity<ProjectDto> insertProject(@RequestBody Project project) {
        logger.info("ProjectController || insertProjectDetails || Inserting the ProjectDetails Info {} // ->", project);
        return new ResponseEntity<>(projectService.insertProject(project), HttpStatus.OK);
    }

    @GetMapping(value = "/getProjectDetails")
    public ResponseEntity<ProjectDto> getProjectDetails(@RequestParam int projectId) {
        logger.info("ProjectController || getProjectDetails || getting the ProjectDetails ->");
        return new ResponseEntity<>(projectService.getProjectDetails(projectId), HttpStatus.OK);
    }

    @PutMapping(value = "/updateProject")
    public ResponseEntity<ProjectDto> updateProject(@RequestBody Project project) {
        logger.info("ProjectController || updateProjectDetails || Updating the ProjectDetails Info {} //->", project);
        return new ResponseEntity<>(projectService.updateProject(project), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteData")
    public String deleteProject(@RequestParam int projectId) {
        logger.info("ProjectController || deleteProjectDetails || Deleting the rProjectDetails {} // ->", projectId);
        projectService.deleteProject(projectId);
        return "Deleted Successfully";
    }
    @PutMapping(value = "/insertImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> insertImage(@RequestParam("image") Optional<MultipartFile> image, @RequestParam("id") int projectId) throws IOException {
        logger.info("ProjectController || insertImage || Inserting Images");
        projectService.insertImage(image,projectId);
        return new ResponseEntity<>("Image Inserted", HttpStatus.OK);

    }
}
