package com.timesheet.module.task.service.impl;

import com.timesheet.module.task.entity.Task;
import com.timesheet.module.task.entity.dto.TaskDto;
import com.timesheet.module.task.repository.TaskRepo;
import com.timesheet.module.task.service.TaskService;
import com.timesheet.module.utils.NullPropertyName;
import com.timesheet.module.utils.exceptions.ServiceException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.timesheet.module.utils.TimeSheetErrorCodes.*;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepo taskRepo;

    @Autowired
    ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    public TaskDto addTask(Task task) {
        logger.info("TaskServiceImpl || add-taskDetails ||Adding the task Details");
        try {
            Task task1 = taskRepo.save(task);
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            TaskDto taskDto = modelMapper.map(task1, TaskDto.class);
            return taskDto;
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Invalid data");
        }
    }

    @Override
    public List<TaskDto> getTaskByProjectId(int projectId) {
        try {
            List<Task> taskList = taskRepo.findByProjectProjectId(projectId).get();
            if (!taskList.isEmpty()) {
                return taskList.stream().map(task -> {
//                    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                    return modelMapper.map(task, TaskDto.class);
                }).collect(Collectors.toList());
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid req/Id not found");
        }
    }


    @Override
    public TaskDto updateTask(Task task) {
        try {
            TaskDto taskDto = new TaskDto();
            Optional<Task> task1 = taskRepo.findById(task.getTaskId());
            if (task1.isPresent()) {
                BeanUtils.copyProperties(task, task1.get(), NullPropertyName.getNullPropertyNames(task));
                logger.info("TaskServiceImpl  || updateTask || DataSet was updated in task=={}", task);
                Task task2 = taskRepo.save(task1.get());
                BeanUtils.copyProperties(task2, taskDto, NullPropertyName.getNullPropertyNames(task2));
                return taskDto;
            } else {
                throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid ID or values");
            }
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid data");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Data not saved");
        }
    }

    @Override
    public String deleteTask(int taskId) {
        logger.info("TaskServiceImpl || deleteTask || Task detail was deleted by particular taskId=={}", taskId);
        try {
            taskRepo.deleteById(taskId);
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid Input");
        }
        return "Task Deleted Successfully";
    }
}
