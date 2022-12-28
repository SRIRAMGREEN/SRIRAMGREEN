package com.timesheet.module.timesheet.repo;

import com.timesheet.module.projectmanager.entity.ProjectManager;
import com.timesheet.module.task.entity.Task;
import com.timesheet.module.timesheet.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimesheetRepo extends JpaRepository<Timesheet,Integer> {
//    @Query(value ="SELECT * FROM Timesheet Where employee_id =?;", nativeQuery = true )
    Optional<Timesheet> findTimesheetByEmployeeId(int id);

    @Query(value = "SELECT * FROM timesheet WHERE timesheet_start_date, timesheet_end_date =?;", nativeQuery = true)
    Optional<List<Timesheet>> findTimesheetByTimesheetDate(String timesheetStartDate,String timesheetEndDate);


    @Query(value = "SELECT * FROM timesheet WHERE employee_id=? AND" + " timesheet_start_date=? AND timesheet_end_date =?;", nativeQuery = true)
    Optional<List<Timesheet>> findTimesheetByEmployeeIdAndDate(int id, String timesheetStartDate, String timesheetEndDate);

//    @Query(value = "SELECT * FROM timesheet WHERE project_manager_id=?")
    Optional<List<Timesheet>> findTimesheetByProjectManagerId(int id);



    Optional<Timesheet> findByTask(Task task);
}
