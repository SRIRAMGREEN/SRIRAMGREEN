package com.timesheet.module.timesheet.repo;

import com.timesheet.module.timesheet.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimesheetRepo extends JpaRepository<Timesheet,Integer> {
//    @Query(value ="SELECT * FROM Timesheet Where employee_id =?;", nativeQuery = true )
    Optional<Timesheet> findTimesheetByEmployeeId(int id);
}
