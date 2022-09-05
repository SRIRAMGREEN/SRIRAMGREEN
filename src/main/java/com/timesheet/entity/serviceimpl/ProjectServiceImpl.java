package com.timesheet.entity.serviceimpl;

import com.timesheet.entity.model.Project;
import com.timesheet.entity.repository.ProjectRepo;
import com.timesheet.entity.service.ProjectService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.timesheet.entity.utils.TimeSheetErrorCodes.*;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepo projectRepo;

    Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Override
    public List<Project> insertProject(List<Project> project) {
        logger.info("ProjectServiceImpl || inserProject ||  Successfully inserted project details {} // ->", project);
        return projectRepo.saveAll(project);
    }

    @Override
    public List<Project> getProjectDetails(int projectId) {
        try {
            Optional<List<Project>> project = projectRepo.findByProjectId(projectId);
            if (!project.get().isEmpty()) {
                return project.get();
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode());
        } catch (Exception e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode());
        }
    }

    @Override
    public Project updateProject(Project project) {
        try {
            Optional<Project> projectData = projectRepo.findById(project.getProjectId());
            if (projectData.isPresent()) {
                logger.info("ProjectServiceImpl || updateData || Updating the  Project");

                return projectRepo.saveAndFlush(project);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode());
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode());
        }
    }

    @Override
    public String deleteProject(int projectId) {
        Optional<Project> projectData = projectRepo.findById(projectId);
        try {
            if (projectData.isPresent()) {
                logger.info("ProjectServiceImpl || deleteData || Deleting the Project id: {}", projectId);
                projectRepo.deleteById(projectId);
            }
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode());

        }
        return "Project Deleted Successfully";
    }

}

