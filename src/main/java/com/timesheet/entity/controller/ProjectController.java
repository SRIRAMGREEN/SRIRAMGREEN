package com.timesheet.entity.controller;

import com.timesheet.entity.model.Project;
import com.timesheet.entity.repository.ProjectRepo;
import com.timesheet.entity.service.ProjectService;
import com.timesheet.entity.service.TimeSheetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Project>> insertProject(@RequestBody List<Project> projectList) {
        logger.info("ProjectController || insertProjectDetails || Inserting the ProjectDetails Info {} // ->", projectList);
        return new ResponseEntity<>(projectService.insertProject(projectList), HttpStatus.OK);
    }

    @GetMapping(value = "/getProjectDetails")
    public ResponseEntity<List<Project>> getProjectDetails(@RequestParam int projectId) {
        logger.info("ProjectController || getProjectDetails || getting the ProjectDetails ->");
        return new ResponseEntity<>(projectService.getProjectDetails(projectId), HttpStatus.OK);
    }

    @PutMapping(value = "/updateProject")
    public ResponseEntity<Project> updateProject(@RequestBody Project project) {
        logger.info("ProjectController || updateProjectDetails || Updating the ProjectDetails Info {} //->", project);
        return new ResponseEntity<>(projectService.updateProject(project), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteData")
    public String deleteProject(@RequestParam int projectId) {
        logger.info("ProjectController || deleteProjectDetails || Deleting the rProjectDetails {} // ->", projectId);
        projectService.deleteProject(projectId);
        return "Deleted Successfully";
    }

}
