package com.timesheet.module.Employee.repository;

import com.timesheet.module.Employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    Optional<List<Employee>> findByEmployeeAddedByAdminTrue();

    Employee findByEmailId(String emailId);
}
