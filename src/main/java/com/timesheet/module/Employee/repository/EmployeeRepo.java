package com.timesheet.module.Employee.repository;

import com.timesheet.module.Employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    Optional<List<Employee>> findByEmployeeAddedByAdminTrue();


    @Query(value ="SELECT * FROM employee Where team_id =?;", nativeQuery = true )
    Optional<List<Employee>> findByTeamId(int teamId);

}
