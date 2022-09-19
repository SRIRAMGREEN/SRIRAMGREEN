package com.timesheet.module.task.controller;

import com.timesheet.module.task.entity.Task;
import com.timesheet.module.task.entity.dto.TaskDto;
import com.timesheet.module.task.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    TaskService taskService;
    Logger logger = LoggerFactory.getLogger(TaskController.class);


    @PostMapping(value = "/insertTask")
    public ResponseEntity<TaskDto> insertTask(@RequestBody Task task) {
        logger.info("TaskController || insertTask || Inserting the task Details from the task");
        return new ResponseEntity<>(taskService.addTask(task), HttpStatus.OK);
    }

    @GetMapping(value = "/getTaskByProjectId")
    public ResponseEntity<List<TaskDto>> getTask(@RequestParam int projectId) {
        logger.info("TaskController || getTask || Getting the task Details from the task");
        return new ResponseEntity<>(taskService.getTaskByProjectId(projectId), HttpStatus.OK);
    }

    @PutMapping(value = "/updateTask")
    public ResponseEntity<TaskDto> updateTask(@RequestBody Task task) {
        logger.info("TaskController || updateTask || Updating the task Details from the ClientEntity");
        return new ResponseEntity<>(taskService.updateTask(task), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteClient")
    public void deleteTask(@RequestParam int taskId) {
        logger.info("TaskController || deleteTask || Deleting the task Details from the Task");
        taskService.deleteTask(taskId);
    }
}

