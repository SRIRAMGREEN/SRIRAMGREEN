package com.timesheet.module.timesheet.service.serviceimpl;

import com.timesheet.module.registration.entity.Registration;
import com.timesheet.module.registration.repo.RegistrationRepo;
import com.timesheet.module.task.entity.Task;
import com.timesheet.module.task.repository.TaskRepo;
import com.timesheet.module.timesheet.dto.TimesheetDto;
import com.timesheet.module.timesheet.dto.TimesheetLogsDto;
import com.timesheet.module.timesheet.entity.Timesheet;
import com.timesheet.module.timesheet.entity.TimesheetLogs;
import com.timesheet.module.timesheet.entity.TimesheetVerificationEmailContext;
import com.timesheet.module.timesheet.repo.TimesheetLogsRepo;
import com.timesheet.module.timesheet.repo.TimesheetRepo;
import com.timesheet.module.timesheet.service.TimesheetService;
import com.timesheet.module.utils.exceptions.ServiceException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.timesheet.module.utils.TimeSheetErrorCodes.DATA_NOT_FOUND;
import static com.timesheet.module.utils.TimeSheetErrorCodes.DATA_NOT_SAVED;

@Service
public class TimeSheetServiceImpl implements TimesheetService {

    @Autowired
    TimesheetRepo timesheetRepo;
    @Autowired
    TimesheetEmailService timesheetEmailService;

    @Autowired
    TaskRepo taskRepo;
    @Autowired
    RegistrationRepo registrationRepo;
    @Autowired
    TimesheetLogsRepo timesheetLogsRepo;
    @Autowired
    ModelMapper modelMapper;
    Logger logger = LoggerFactory.getLogger(TimeSheetServiceImpl.class);
    @Value("${Timesheet.timesheetURL}")
    private String timesheetURL;

    @Override
    @Transactional
    public List<TimesheetLogsDto> updateTimesheet(List<TimesheetLogs> timesheetLogs) throws MessagingException {

        logger.info("TimeSheetServiceImpl || addClientDetails ||Adding the client Details");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        AtomicLong totalHours = new AtomicLong(0l);
        List<TimesheetLogs> savedTimesheetLogs = new ArrayList<>();
        List<TimesheetLogsDto> savedTimesheetLogsDto = new ArrayList<>();
        try {
            savedTimesheetLogs = timesheetLogs.stream().map(timesheetLogs1 -> {
                totalHours.addAndGet(timesheetLogs1.getHours());
                Timesheet timesheet = timesheetRepo.findByTask(timesheetLogs1.getTask()).get();
                timesheetLogs1.setTimesheet(timesheet);
                TimesheetLogs savedTimeSheetLog = timesheetLogsRepo.save(timesheetLogs1);
                savedTimesheetLogsDto.add(modelMapper.map(savedTimeSheetLog,TimesheetLogsDto.class));
                return savedTimeSheetLog;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(),"TimesheetLogs Not Saved ");
        }
        List<LocalDateTime> collect = savedTimesheetLogs.stream().map(timesheetLogs1 -> timesheetLogs1.getDate()).collect(Collectors.toList());
        Collections.sort(collect);
        Task task = taskRepo.findById(timesheetLogs.get(0).getTask().getTaskId()).orElseThrow(() -> new IllegalArgumentException("Task Id not Found"));
        Timesheet timesheet1 = timesheetRepo.findByTask(timesheetLogs.get(0).getTask()).orElseThrow(() -> new IllegalArgumentException("Timesheet ID Invalid!!"));
        timesheet1.setTimesheetLogsList(timesheetLogs);

        if (Collections.max(collect).isAfter(task.getTaskEndDate())) timesheet1.setTimeLimitExceeded(true);

        timesheet1.setTimesheetStartDate(collect.get(0));
        timesheet1.setTimesheetEndDate(Collections.max(collect));
        timesheet1.setTotalHours(totalHours.get());
        Timesheet savedTimesheet = new Timesheet();
        try {
            savedTimesheet = timesheetRepo.save(timesheet1);
            Registration registration = registrationRepo.findById(timesheet1.getManagerId()).orElseThrow(() -> new IllegalArgumentException(("Timesheet not Found!!")));
            TimesheetVerificationEmailContext email = new TimesheetVerificationEmailContext();
            email.init(registration);
            email.buildVerificationUrl(timesheetURL , registration.getEmailId());
            timesheetEmailService.sendMail(email);
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(),"Data not saved");
        }
        return savedTimesheetLogsDto;
    }

    @Transactional
    @Override
    public TimesheetDto getTimesheetById(int timesheetId) {
        try {
            Optional<Timesheet> timesheet = timesheetRepo.findById(timesheetId);
            if (timesheet.isPresent()) {
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                TimesheetDto timesheetDto = modelMapper.map(timesheet, TimesheetDto.class);
                logger.info("TimeSheetServiceImpl || getTimesheetById || get the Timesheet Info");
                return timesheetDto;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid req/Id not found");
        }
    }

    @Override
    @Transactional
    public TimesheetDto getTimesheetByEmployeeId(int id) {
        logger.info("TimeSheetServiceImpl || getTimesheetByEmployeeId || get  Timesheet by EmployeeId ");
        try {
            Optional<Timesheet> timesheet = timesheetRepo.findTimesheetByEmployeeId(id);
            if (timesheet.isPresent()) {
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                return modelMapper.map(timesheet, TimesheetDto.class);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid req/Id not found");
        }
    }

    @Override
    public String deleteTimesheet(int timesheetId) {
        logger.info("TimeSheetServiceImpl || deleteTimesheet || Timesheet detail was deleted by particular timesheetID=={}", timesheetId);
        try {
            timesheetRepo.deleteById(timesheetId);
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid ID");
        }
        return "Timesheet Deleted Successfully";
    }

    @Override
    @Transactional
    public TimesheetDto updateTimesheetStatus(int timesheet_id, boolean status) throws MessagingException {
        Timesheet timesheet = timesheetRepo.findById(timesheet_id).orElseThrow(() -> new IllegalArgumentException("Timesheet id not found"));
        timesheet.setTimesheetStatus(status);
        Timesheet timesheet1 = new Timesheet();
        try {
            timesheet1 = timesheetRepo.save(timesheet);
            Registration registration = registrationRepo.findById(timesheet.getEmployee().getId()).orElseThrow(() -> new IllegalArgumentException(("Employee not Found!!")));
            TimesheetVerificationEmailContext email = new TimesheetVerificationEmailContext();
            email.initStatus(registration);
//            email.buildVerificationUrl(timesheetURL , registration.getEmailId());
            timesheetEmailService.sendMail(email);
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(),"timesheet status not saved");
        }
        return  modelMapper.map(timesheet1,TimesheetDto.class);
    }

}



