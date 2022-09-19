package com.timesheet.module.task.service;

import com.timesheet.module.task.entity.Task;
import com.timesheet.module.task.entity.dto.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto addTask(Task task);

    List<TaskDto> getTaskByProjectId(int projectId);

//    List<TaskDto> getAllTaskDetails();

    TaskDto updateTask(Task task);

    String deleteTask(int taskId);
}
