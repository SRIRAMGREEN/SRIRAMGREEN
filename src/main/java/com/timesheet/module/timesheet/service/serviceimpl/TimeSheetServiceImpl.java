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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.MessagingException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.timesheet.module.utils.TimeSheetErrorCodes.*;

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
                timesheet.getTask().setStatus("In progress");
                TimesheetLogs savedTimeSheetLog = timesheetLogsRepo.save(timesheetLogs1);
                savedTimesheetLogsDto.add(modelMapper.map(savedTimeSheetLog, TimesheetLogsDto.class));
                return savedTimeSheetLog;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), e.getMessage() + ": TimesheetLogs Not Saved ");
        }
        List<String> collect = savedTimesheetLogs.stream().map(timesheetLogs1 -> timesheetLogs1.getDate()).collect(Collectors.toList());
        Collections.sort(collect);

        Task task = taskRepo.findById(timesheetLogs.get(0).getTask().getTaskId()).orElseThrow(() -> new IllegalArgumentException("Task Id not Found"));
        Timesheet timesheet1 = timesheetRepo.findByTask(timesheetLogs.get(0).getTask()).orElseThrow(() -> new IllegalArgumentException("Timesheet ID Invalid!!"));
        timesheet1.setTimesheetLogsList(timesheetLogs);

        if (Collections.max(collect).equals(task.getTaskEndDate())) timesheet1.setTimeLimitExceeded(true);

        timesheet1.setTimesheetStartDate(collect.get(0));
        timesheet1.setTimesheetEndDate(Collections.max(collect));
        timesheet1.setTotalHours(totalHours.get());
        timesheet1.setTimesheetStatus(null);
        Timesheet savedTimesheet = new Timesheet();
        try {
            savedTimesheet = timesheetRepo.save(timesheet1);
            Registration registration = registrationRepo.findById(timesheet1.getProjectManager().getId()).orElseThrow(() -> new IllegalArgumentException(("Timesheet not Found!!")));
            TimesheetVerificationEmailContext email = new TimesheetVerificationEmailContext();
            email.init(registration);
            email.buildVerificationUrl(timesheetURL, registration.getEmailId());
            timesheetEmailService.sendMail(email);
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Data not saved");
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
    @Transactional
    public List<TimesheetDto> getTimesheetByTimesheetDate(String timesheetStartDate, String timesheetEndDate) {
        logger.info("TimeSheetServiceImpl || getTimesheetByEmployeeId || get  Timesheet by EmployeeId ");
        try {
            Optional<List<Timesheet>> timesheetList = timesheetRepo.findTimesheetByTimesheetDate(timesheetStartDate,timesheetEndDate);
            List<TimesheetDto> timesheetDtoList = new ArrayList<>();
            if (timesheetList.isPresent()) {
                for (Timesheet timesheet : timesheetList.get()) {
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                TimesheetDto timesheetDto = modelMapper.map(timesheet, TimesheetDto.class);
                timesheetDtoList.add(timesheetDto);
            }
            return timesheetDtoList;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid req/Id not found");
        }
    }

    @Override
    @Transactional
    public List<TimesheetDto> getTimesheetByEmployeeIdAndDate(int id,String timesheetStartDate, String timesheetEndDate) {

            try {
                Optional<List<Timesheet>> timesheetList = timesheetRepo.findTimesheetByEmployeeIdAndDate(id, timesheetStartDate, timesheetEndDate);
                List<TimesheetDto> timesheetDtoList = new ArrayList<>();
                if (timesheetList.isPresent()) {
                    for (Timesheet timesheet : timesheetList.get()) {
                        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                        TimesheetDto timesheetDto = modelMapper.map(timesheet, TimesheetDto.class);
                        timesheetDtoList.add(timesheetDto);
                    }
                    return timesheetDtoList;
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
    @Transactional
    public List<TimesheetDto> getTimesheetByProjectManagerId(int id) {
        try {
            Optional<List<Timesheet>> timesheetList = timesheetRepo.findTimesheetByProjectManagerId(id);
            List<TimesheetDto> timesheetDtoList = new ArrayList<>();
            if (timesheetList.isPresent()) {
                for (Timesheet timesheet : timesheetList.get()) {
                    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                    TimesheetDto timesheetDto = modelMapper.map(timesheet, TimesheetDto.class);
                    timesheetDtoList.add(timesheetDto);
                }
                return timesheetDtoList;
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
    public TimesheetDto updateTimesheetStatus(int timesheet_id, String status) throws MessagingException {
        Timesheet timesheet = timesheetRepo.findById(timesheet_id).orElseThrow(() -> new IllegalArgumentException("Timesheet id not found"));
        timesheet.setTimesheetStatus(status);
        logger.info("TimeSheetServiceImpl || updateTimesheetStatus || TimesheetStatus detail for timesheet =={}", status);
        String rejectStatus = "Reject";
        String approveStatus = "Approve";
        try {
            if (status.equals(rejectStatus)) {
                timesheet.getTask().setStatus("yet to  start");
                timesheetLogsRepo.deleteAll(timesheetLogsRepo.findTimesheetLogsByTimesheetId(timesheet.getId()).get());
                timesheet = timesheetRepo.save(timesheet);
            } else if (status.equals(approveStatus)) {
                timesheet.getTask().setStatus("completed");
                timesheet = timesheetRepo.save(timesheet);
            } else {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            Registration registration = registrationRepo.findById(timesheet.getEmployee().getId()).orElseThrow(() -> new IllegalArgumentException(("Employee not Found!!")));
            TimesheetVerificationEmailContext email = new TimesheetVerificationEmailContext();
            if (timesheet.getTimesheetStatus().equals(approveStatus)) {
                email.initStatusApproved(registration);
            } else if (timesheet.getTimesheetStatus().equals(rejectStatus)) {
                email.initStatusReject(registration);
            }
            timesheetEmailService.sendMailStatus(email);
            return modelMapper.map(timesheet, TimesheetDto.class);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "TimesheetStatus not Saved");
        }
    }
@Transactional
@Override
    public List<Timesheet> listAll() {
        return timesheetRepo.findAll(Sort.by("employee_id").ascending());
    }
}


