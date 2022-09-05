package com.timesheet.entity.repository;

import com.timesheet.entity.model.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSheetRepo extends JpaRepository<TimeSheet,Integer> {
}
