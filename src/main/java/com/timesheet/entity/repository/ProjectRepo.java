package com.timesheet.entity.repository;

import com.timesheet.entity.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepo extends JpaRepository<Project,Integer> {
//    Optional <Project> findById(int projectId);


    Optional<Project> findByProjectId(int projectId);

//    Integer deleteById(int projectId);
}
