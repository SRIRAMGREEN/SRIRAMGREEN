package com.timesheet.module.project.service.impl;

import com.timesheet.module.project.dto.ProjectDto;
import com.timesheet.module.project.entity.Project;
import com.timesheet.module.project.repository.ProjectRepo;
import com.timesheet.module.project.service.ProjectService;
import com.timesheet.module.utils.NullPropertyName;
import com.timesheet.module.utils.exceptions.ServiceException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.timesheet.module.utils.TimeSheetErrorCodes.*;
import static com.timesheet.module.utils.TimesheetConstants.IMAGE_REGEX;


@Service
public class ProjectServiceImpl implements ProjectService {


    @Autowired
    ProjectRepo projectRepo;

    @Autowired
    ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Override
    public ProjectDto insertProject(Project project) {
        try {
            logger.info("ProjectServiceImpl || insertData || Inserting the Project list: {}", project);
            Project project1 = projectRepo.save(project);
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            ProjectDto projectDto = modelMapper.map(project1, ProjectDto.class);
            return projectDto;
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode());
        }
    }

    @Override
    public List<ProjectDto> getProjectDetailsByClientId(int clientId) {
        try {
            List<Project> projects = projectRepo.findByClientId(clientId).get();
            if (!projects.isEmpty()) {
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                return projects.stream().map(project -> {
                    return modelMapper.map(project, ProjectDto.class);
                }).collect(Collectors.toList());
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid req/Id not found");
        } catch (Exception e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid req");
        }
    }

    @Override
    public ProjectDto updateProject(Project project) {
        try {
            ProjectDto projectDto = new ProjectDto();
            Optional<Project> project1 = projectRepo.findById(project.getProjectId());
            if (project1.isPresent()) {
                BeanUtils.copyProperties(project, project1.get(), NullPropertyName.getNullPropertyNames(project));
                logger.info("ProjectServiceImpl  || updateProject || DataSet was updated in Project=={}", project);
                Project project2 = projectRepo.save(project1.get());
                BeanUtils.copyProperties(project2, projectDto, NullPropertyName.getNullPropertyNames(project2));
                return projectDto;
            } else {
                throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid ID or values");
            }
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid data");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Data not saved");
        }
    }

    @Override
    public String deleteProject(int projectId) {
        Optional<Project> project = projectRepo.findById(projectId);
        if (project.isPresent()) {
            logger.info("ProjectServiceImpl || deleteData || Deleting the project id: {}", projectId);
            try {
                projectRepo.deleteById(projectId);

            } catch (NullPointerException e) {
                throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
            } catch (Exception e) {
                throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Data Not Found");
            }
        }
        return "deleted";
    }

    @Transactional
    @Override
    public Boolean insertImage(Optional<MultipartFile> image, int projectId) {
        Optional<Project> project = projectRepo.findById(projectId);
        if (project.isPresent()) {
            try {
                Project projects = project.get();
                if (image.isPresent()) {
                    projects.setImage(image.get().getBytes());
                    projectRepo.save(projects);
                } else {
                    throw new NullPointerException();
                }
            } catch (NullPointerException e) {
                throw new ServiceException(INVALID_REQUEST.getErrorCode());
            } catch (Exception e) {
                throw new ServiceException(IMAGE_REGEX, "Invalid image format");
            }
        } else {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid data");
        }
        return true;
    }

}

