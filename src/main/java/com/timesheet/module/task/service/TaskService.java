package com.timesheet.module.task.service;

import com.timesheet.module.task.entity.Task;
import com.timesheet.module.task.dto.TaskDto;

import javax.mail.MessagingException;
import java.util.List;

public interface TaskService {

    TaskDto addTask(Task task) throws MessagingException;

    TaskDto getTaskDetail(int taskId);

    List<TaskDto> getTaskByProjectId(int projectId);

    List<TaskDto> getTaskByEmployeeId(int id);
    List<TaskDto> getTaskByProjectManagerId(int id);
    List<TaskDto>getAllTasks();

    TaskDto updateTask(Task task);

    String deleteTask(int taskId);

    double PercentageAllocation(int taskId, int totalWeekDays);
}
