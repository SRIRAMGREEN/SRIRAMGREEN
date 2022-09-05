package com.timesheet.entity.repository;

import com.timesheet.entity.model.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperAdminRepo extends JpaRepository<SuperAdmin, Integer> {
    SuperAdmin findByEmail(String email);

    Integer findById(int id);

}
