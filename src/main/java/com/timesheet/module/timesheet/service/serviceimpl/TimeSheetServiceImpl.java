package com.timesheet.module.timesheet.service.serviceimpl;

import com.timesheet.module.registration.entity.Registration;
import com.timesheet.module.registration.repo.RegistrationRepo;
import com.timesheet.module.task.repository.TaskRepo;
import com.timesheet.module.timesheet.dto.TimesheetDto;
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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.timesheet.module.utils.TimeSheetErrorCodes.DATA_NOT_FOUND;


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
    public TimesheetDto updateTimesheet(Timesheet timesheet) throws MessagingException {
        logger.info("TimeSheetServiceImpl || addClientDetails ||Adding the client Details");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        AtomicLong totalHours = new AtomicLong(0l);
        List<TimesheetLogs> timesheetLogs;
        try {
            timesheetLogs = timesheet.getTimesheetLogsList().stream().map(timesheetLogs1 -> {
                totalHours.addAndGet(timesheetLogs1.getHours());
                timesheetLogs1.setTimesheet(timesheet);
                return timesheetLogsRepo.save(timesheetLogs1);
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("");
        }
        Timesheet timesheet1 = timesheetRepo.findById(timesheet.getId()).orElseThrow(() -> new IllegalArgumentException("Timesheet ID Invalid!!"));
        timesheet.setTimesheetLogsList(timesheetLogs);
        timesheet.setTotalHours(totalHours.get());
        timesheet.setManagerId(timesheet1.getManagerId());
        timesheet.setEmployee(timesheet1.getEmployee());
        Timesheet savedTimesheet = timesheetRepo.save(timesheet);
        Registration registration = registrationRepo.findById(timesheet.getManagerId()).orElseThrow(() -> new IllegalArgumentException(("Timesheet not Found!!")));
        TimesheetVerificationEmailContext email = new TimesheetVerificationEmailContext();
        email.init(registration);
        email.buildVerificationUrl(timesheetURL, registration.getEmailId());
        timesheetEmailService.sendMail(email);
        return modelMapper.map(savedTimesheet, TimesheetDto.class);
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


}



