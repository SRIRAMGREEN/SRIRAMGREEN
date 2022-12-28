package com.timesheet.module.timesheet.service.serviceimpl;

import com.timesheet.module.timesheet.dto.TimesheetLogsDto;
import com.timesheet.module.timesheet.entity.TimesheetLogs;
import com.timesheet.module.timesheet.repo.TimesheetLogsRepo;
import com.timesheet.module.timesheet.service.TimesheetLogsService;
import com.timesheet.module.utils.exceptions.ServiceException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timesheet.module.utils.TimeSheetErrorCodes.*;

@Service
public class TimesheetLogsServiceImpl implements TimesheetLogsService {

    @Autowired
    TimesheetLogsRepo timesheetLogsRepo;

    Logger logger = LoggerFactory.getLogger(TimesheetLogsServiceImpl.class);
    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public List<TimesheetLogsDto> getLogsByTimesheetId(int timesheetId) {
        logger.info("TimesheetLogsServiceImpl || getTimesheetLogs || Updating the timesheetLogs Info");
        try {
            Optional<List<TimesheetLogs>> timesheetLogs = timesheetLogsRepo.findTimesheetLogsByTimesheetId(timesheetId);
            List<TimesheetLogsDto> timesheetLogsList = new ArrayList<>();
            if (timesheetLogs.isPresent()) {
                for(TimesheetLogs timesheetLogs1 : timesheetLogs.get()) {
                    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                    TimesheetLogsDto timesheetLogsDto = modelMapper.map(timesheetLogs1, TimesheetLogsDto.class);
                    timesheetLogsList.add(timesheetLogsDto);
                }
                return timesheetLogsList;

            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid request/Id not found");
        } catch (Exception e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid data");
        }
    }

    @Override
    public TimesheetLogsDto updateTimesheetLogs(TimesheetLogs timesheetLogs) {
        try {

            Optional<TimesheetLogs> timesheetLogs1 = timesheetLogsRepo.findById(timesheetLogs.getId());
            if (timesheetLogs1.isPresent()) {
                logger.info("TimesheetLogsServiceImpl  || updateTimesheetLogs || Data was updated timesheetLogs == {}", timesheetLogs1);
                modelMapper.map(timesheetLogs1, TimesheetLogsDto.class);
                TimesheetLogs timesheetLogs2 = timesheetLogsRepo.save(timesheetLogs1.get());
                return modelMapper.map(timesheetLogs2, TimesheetLogsDto.class);
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
    public String deleteTimesheetLogs(int id) {
        logger.info("TimesheetLogsServiceImpl || deleteTimesheetLogs || TimesheetLogs detail was deleted by particular timesheetLogs=={}", id);
        try {
            timesheetLogsRepo.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid ID");
        }
        return "TimesheetLogs Deleted Successfully";
    }

}
