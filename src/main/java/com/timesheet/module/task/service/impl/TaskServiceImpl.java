package com.timesheet.module.task.service.impl;


import com.timesheet.module.Employee.repository.EmployeeRepo;
import com.timesheet.module.registration.entity.Registration;
import com.timesheet.module.registration.repo.RegistrationRepo;
import com.timesheet.module.task.dto.TaskDto;
import com.timesheet.module.task.entity.Task;
import com.timesheet.module.task.entity.TaskVerificationEmailContextTask;
import com.timesheet.module.task.repository.TaskRepo;
import com.timesheet.module.task.service.TaskService;
import com.timesheet.module.timesheet.entity.Timesheet;
import com.timesheet.module.timesheet.repo.TimesheetRepo;
import com.timesheet.module.utils.NullPropertyName;
import com.timesheet.module.utils.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timesheet.module.utils.TimeSheetErrorCodes.*;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskEmailService taskEmailService;

    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    TaskRepo taskRepo;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    RegistrationRepo registrationRepo;
    Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    @Autowired
    TimesheetRepo timesheetRepo;
    @Value("${Timesheet.taskURL}")
    private String taskURL;

    @Override
    @Transactional
    public TaskDto addTask(Task task) throws MessagingException {

        logger.info("TaskServiceImpl || addTask ||Adding the client Details");
        Timesheet timesheet = new Timesheet();
        timesheet.setManagerId(getLoggerInUser().getId());
        timesheet.setEmployee(employeeRepo.findById(task.getEmployee().getId()).orElseThrow(() -> new IllegalArgumentException("Employee Not Found")));
        Timesheet savedTimesheet = timesheetRepo.save(timesheet);
        task.setTimesheet(savedTimesheet);
        Task savedTask = taskRepo.save(task);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.map(savedTask, TaskDto.class);
        Registration registration = registrationRepo.findById(task.getEmployee().getId()).orElseThrow(() -> new IllegalArgumentException(("Employee not Found!!")));
        TaskVerificationEmailContextTask email = new TaskVerificationEmailContextTask();
        email.init(registration);
        email.buildVerificationUrl(taskURL, registration.getEmailId());
        taskEmailService.sendMail(email);
        return modelMapper.map(savedTask, TaskDto.class);

    }

    @Override
    @Transactional
    public List<TaskDto> getTaskByProjectId(int projectId) {
        try {
            Optional<List<Task>> task = taskRepo.findByProjectProjectId(projectId);
            List<TaskDto> taskDtoList = new ArrayList<>();
            if (task.isPresent()) {
                for (Task task1 : task.get()) {
                    TaskDto taskDto = modelMapper.map(task1, TaskDto.class);
                    taskDtoList.add(taskDto);
                }
                return taskDtoList;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid req/Id not found");
        } catch (Exception e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid req");
        }

    }

    @Transactional
    @Override
    public TaskDto getTaskByEmployeeId(int id) {
        logger.info("TaskServiceImpl || getTaskByEmployeeId ||Adding the task Details");
        try {
            Optional<Task> taskList = taskRepo.findTaskByEmployeeId(id);
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            if (taskList.isPresent()) {
                return modelMapper.map(taskList.get(), TaskDto.class);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid req/Id not found");
        }
    }

    @Override
    public TaskDto updateTask(Task task) {
        logger.info("TaskServiceImpl || updateTask ||Adding the task Details");
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
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid ID");
        }
        return "Task Deleted Successfully";
    }

    @Transactional
    @Override
    public double PercentageAllocation(int taskId, int date, int month, int year, int totalWeekDays) {
        logger.info("TaskServiceImpl  || updateTask || DataSet was updated in task=={}", taskId);
        double percentage;
        int diffInDays;
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.of(year, month, date);
        logger.info("TaskServiceImpl  || getNumberOfDays || getting the count of days between two dates");
        diffInDays = (int) ChronoUnit.DAYS.between(date1, date2);
        logger.info("TaskServiceImpl  || getPercentage || getting the percentage of days count");
        percentage = (100 * diffInDays) / totalWeekDays;
        Optional<Task> percent = taskRepo.findById(taskId);
        percent.get().setTaskEffort(String.valueOf(diffInDays));
        percent.get().setPercentageOfAllocation(percentage);
        Task task = taskRepo.save(percent.get());
        taskRepo.save(task);
        logger.info("TaskServiceImpl  || getNumberOfDays || getting the count of days between two dates 0-{}", percent);
        return percentage;
    }

    private Registration getLoggerInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginId = auth.getName();
        return registrationRepo.findByLoginId(loginId).get();
    }

}
