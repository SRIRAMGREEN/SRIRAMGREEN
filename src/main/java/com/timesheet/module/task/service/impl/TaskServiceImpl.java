package com.timesheet.module.task.service.impl;

import com.timesheet.module.Employee.repository.EmployeeRepo;
import com.timesheet.module.client.dto.ClientDto;
import com.timesheet.module.projectmanager.repository.ProjectManagerRepo;
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
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
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
    ProjectManagerRepo projectManagerRepo;

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
        Task savedTask;
        Timesheet savedTimesheet;
        try {
//            timesheet.setManagerId(getLoggedInUser().getId());
            timesheet.setProjectManager(projectManagerRepo.findById(task.getProjectManager().getId()).orElseThrow(() -> new IllegalArgumentException("ProjectManager Not Found")));
            timesheet.setEmployee(employeeRepo.findById(task.getEmployee().getId()).orElseThrow(() -> new IllegalArgumentException("Employee Not Found")));
            savedTimesheet = timesheetRepo.save(timesheet);
            task.setTimesheet(savedTimesheet);

        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "timesheet Not Saved ");
        }
        try {
            savedTask = taskRepo.save(task);
            savedTimesheet.setTask(savedTask);
            Timesheet savedTimesheet2 = timesheetRepo.save(savedTimesheet);
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            modelMapper.map(savedTask, TaskDto.class);
            Registration registration = registrationRepo.findById(task.getEmployee().getId()).orElseThrow(() -> new IllegalArgumentException(("Employee not Found!!")));
            TaskVerificationEmailContextTask email = new TaskVerificationEmailContextTask();
            email.init(registration);
            email.buildVerificationUrl(taskURL, registration.getEmailId());
            taskEmailService.sendMail(email);
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Data not saved");
        }
        return modelMapper.map(savedTask, TaskDto.class);

    }
    @Override
    @Transactional
    public List<TaskDto> getTaskByProjectManagerId(int id) {
        try {
            Optional<List<Task>> tasks = taskRepo.findTaskByProjectManagerId(id);
            List<TaskDto> taskDtoList = new ArrayList<>();
            if (tasks.isPresent()) {
                for (Task task : tasks.get()) {
                    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                    TaskDto taskDto = modelMapper.map(task, TaskDto.class);
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
    @Override
    public TaskDto getTaskDetail(int taskId) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task Id Not Found"));
        return modelMapper.map(task, TaskDto.class);

    }

    @Override
    @Transactional
    public List<TaskDto> getTaskByProjectId(int projectId) {
        try {
            Optional<List<Task>> task = taskRepo.findTaskProjectId(projectId);
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
    public List<TaskDto> getTaskByEmployeeId(int id) {
        logger.info("TaskServiceImpl || getTaskByEmployeeId ||Adding the task Details");
        try {
            Optional<List<Task>> taskList = taskRepo.findTaskByEmployeeId(id);
            List<TaskDto> taskDtoList = new ArrayList<>();

            if (taskList.isPresent()) {
                for (Task task : taskList.get()) {
                    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                    TaskDto taskDto = modelMapper.map(task, TaskDto.class);
                    taskDtoList.add(taskDto);
                }
                return taskDtoList;

            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid req/Id not found");
        }
    }

    @Override
    public List<TaskDto> getAllTasks() {
        logger.info("TaskServiceImpl || getAllTasks || Get all task from the taskEntity");
        try {
            Optional<List<Task>> taskList = Optional.ofNullable(taskRepo.findAll());
            List<TaskDto> taskDtoList = new ArrayList<>();
            if (taskList.isPresent()) {
                for (Task task : taskList.get()) {
                    TaskDto taskDto = modelMapper.map(task, TaskDto.class);
                    taskDtoList.add(taskDto);
                }
                return taskDtoList;
            } else {
                throw new ServiceException(DATA_NOT_FOUND.getErrorCode());
            }
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
        } catch (ServiceException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "No data");
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
    public double PercentageAllocation(int taskId, int totalWeekDays) {
        logger.info("TaskServiceImpl  || updateTask || DataSet was updated in task=={}", taskId);
        double percentage;
        int diffInDays;
        logger.info("TaskServiceImpl  || getNumberOfDays || getting the count of days between two dates");
        Task percent = taskRepo.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Invalid Task Id"));
        Date taskStartDate = percent.getTaskStartDate();
        Date taskEndDate = percent.getTaskEndDate();
        diffInDays = (int) ChronoUnit.DAYS.between((Temporal) taskStartDate, (Temporal) taskEndDate);
        percentage = (100 * diffInDays) / totalWeekDays;
        percent.setTaskEffort(String.valueOf(diffInDays));
        percent.setPercentageOfAllocation(percentage);
        Task task = taskRepo.save(percent);
        taskRepo.save(task);
        logger.info("TaskServiceImpl  || getNumberOfDays || getting the count of days between two dates 0-{}", percent);
        return percentage;
    }

//    private Registration getLoggedInUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String loginId = auth.getName();
//        return registrationRepo.findByLoginId(loginId);
//    }

}
