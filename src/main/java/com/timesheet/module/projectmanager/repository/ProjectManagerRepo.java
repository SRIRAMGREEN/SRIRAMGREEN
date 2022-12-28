package com.timesheet.module.projectmanager.repository;


import com.timesheet.module.projectmanager.entity.ProjectManager;
import com.timesheet.module.timesheet.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProjectManagerRepo extends JpaRepository<ProjectManager,Integer> {

    Optional<List<ProjectManager>> findByProjectManagerAddedByAdminTrue();

    ProjectManager findByProjectManagerName(String projectManagerName);

}
