package com.timesheet.entity.registration.repo;

import com.timesheet.entity.registration.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository<Roles,Integer> {
}
