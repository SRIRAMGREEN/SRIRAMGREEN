package com.timesheet.module.timesheet.repo;

import com.timesheet.module.timesheet.entity.TimesheetLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimesheetLogsRepo extends JpaRepository<TimesheetLogs,Integer > {

    Optional<List<TimesheetLogs>> findTimesheetLogsByTimesheetId(int timesheet_id);
}
