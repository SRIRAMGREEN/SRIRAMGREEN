package com.timesheet.module.projectmanager.controller;

import com.timesheet.module.projectmanager.entity.ProjectManager;
import com.timesheet.module.projectmanager.entity.dto.ProjectManagerDto;
import com.timesheet.module.projectmanager.service.ProjectManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/projectManager")
public class ProjectManagerController {

    @Autowired
    ProjectManagerService projectManagerService;

    Logger logger = LoggerFactory.getLogger(ProjectManagerController.class);


    @GetMapping(value = "/getProjectManagerDetails")
    public ResponseEntity<ProjectManagerDto> getProjectManagerDetails(@RequestParam int projectManagerID) {
        logger.info("ProjectManagerController || getProjectManagerDetails || getting the Client details ");
        return new ResponseEntity<>(projectManagerService.getProjectManagerDetails(projectManagerID), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllProjectManagerDetails")
    public ResponseEntity<List<ProjectManagerDto>> getAllProjectManagerDetail() {
        logger.info("ProjectManagerController || getAllProjectManagerDetails || getting the project Details ");
        return new ResponseEntity<>(projectManagerService.getAllProjectManagerDetails(), HttpStatus.OK);
    }

    @PutMapping(value = "/updateProjectManagerDetails")
    public ResponseEntity<ProjectManagerDto> updateProjectManagerDetails(@RequestBody ProjectManager projectManager) {
        logger.info("ProjectManagerController || updateProjectManagerDetails || Updating the updateProjectManager Info {} ", projectManager);
        return new ResponseEntity<>(projectManagerService.updateProjectManager(projectManager), HttpStatus.OK);
    }



    @DeleteMapping(value = "/deleteProjectManager")
    public String deleteProjectManagerDetails(@RequestParam int projectManagerId) {
        logger.info("ProjectController || deleteProjectManagerDetails || Deleting the project manager Info {} ", projectManagerId);
        projectManagerService.deleteProjectManager(projectManagerId);
        return "Project Deleted Successfully";
    }
}
