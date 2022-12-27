package com.timesheet.module.task.service;

import com.timesheet.module.task.entity.Task;
import com.timesheet.module.task.dto.TaskDto;

import javax.mail.MessagingException;
import java.util.List;

public interface TaskService {

    TaskDto addTask(Task task) throws MessagingException;

    List<TaskDto> getTaskByProjectId(int projectId);

    TaskDto getTaskByEmployeeId(int id);

    TaskDto updateTask(Task task);

    String deleteTask(int taskId);

    double PercentageAllocation(int taskId,int date,int month, int year, int totalWeekDays);
}
