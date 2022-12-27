package com.timesheet.module.task.controller;

import com.timesheet.module.task.entity.Task;
import com.timesheet.module.task.dto.TaskDto;
import com.timesheet.module.task.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    TaskService taskService;
    Logger logger = LoggerFactory.getLogger(TaskController.class);


    @PostMapping(value = "/insertTask")
    public ResponseEntity<TaskDto> insertTask(@RequestBody Task task) throws MessagingException {
        logger.info("TaskController || insertTask || Inserting the task Details from the task");
        return new ResponseEntity<>(taskService.addTask(task), HttpStatus.OK);
    }

    @GetMapping(value = "/getTaskByProjectId")
    public ResponseEntity<List<TaskDto>> getTaskByProjectId(@RequestParam int projectId) {
        logger.info("TaskController || getTaskByProjectId || Getting the task Details from the projectId");
        return new ResponseEntity<>(taskService.getTaskByProjectId(projectId), HttpStatus.OK);
    }

    @GetMapping(value = "/getTaskByEmployeeId")
    public ResponseEntity<TaskDto> getTaskByEmployeeId(@RequestParam int id) {
        logger.info("TaskController || getTaskByEmployeeId || Getting the task Details from the EmployeeId");
        return new ResponseEntity<>(taskService.getTaskByEmployeeId(id), HttpStatus.OK);
    }

    @PutMapping(value = "/updateTask")
    public ResponseEntity<TaskDto> updateTask(@RequestBody Task task) {
        logger.info("TaskController || updateTask || Updating the task Details from the task");
        return new ResponseEntity<>(taskService.updateTask(task), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteTaskData")
    public String deleteTask(@RequestParam int taskId) {
        logger.info("TaskController || deleteTask || Deleting the task Details from the Task");
        taskService.deleteTask(taskId);
        return "Task Data Deleted Successfully";
    }
    @PutMapping(value = "/percentageAllocation")
    public double PercentageAllocation(@RequestParam int taskId,int date,int month, int year, int totalWeekDays) {
        logger.info("Task controller || percentageAllocation || Updating the percentageAllocation Details to the Task Entity");
        return (taskService.PercentageAllocation(taskId,date,month,year,totalWeekDays));
    }
}

