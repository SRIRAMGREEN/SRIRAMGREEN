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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        logger.info("ProjectServiceImpl || insertProject ||Adding the project Details");
        try {
            Project project1 = projectRepo.save(project);
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            return modelMapper.map(project1, ProjectDto.class);
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Invalid data's");
        }
    }

    @Override
    public List<ProjectDto> getProjectByProjectManagerId(int id) {
        try {
            Optional<List<Project>> projects = projectRepo.findProjectByProjectManagerId(id);
            List<ProjectDto> projectDtoList = new ArrayList<>();
            if (projects.isPresent()) {
                for (Project project : projects.get()) {
                    ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
                    projectDtoList.add(projectDto);
                }
                return projectDtoList;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid req/Id not found");
        } catch (Exception e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid req");
        }

    }

    @Transactional
    @Override
    public List<ProjectDto> getProjectDetailsByClientId(int clientId) {
        try {
            Optional<List<Project>> projects = projectRepo.findProjectByClientId(clientId);
            List<ProjectDto> projectDtoList = new ArrayList<>();
            if (projects.isPresent()) {
                for (Project project : projects.get()) {
                    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                    ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
                    projectDtoList.add(projectDto);
                }
                return projectDtoList;
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
    public List<ProjectDto> getAllProjectDetails() {
        logger.info("ProjectServiceImpl || getAllProjectDetails || Get all project manager from the ProjectDetails");
        try {
            Optional<List<Project>> project = Optional.of(projectRepo.findAll());
            List<ProjectDto> projectDtoList = new ArrayList<>();
            if (!project.get().isEmpty()) {
                for (Project project1 : project.get()) {
                    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                    ProjectDto projectDto = modelMapper.map(project1, ProjectDto.class);
                    projectDtoList.add(projectDto);
                }
                return projectDtoList;
            } else {
                throw new ServiceException(DATA_NOT_FOUND.getErrorCode());
            }
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
        } catch (ServiceException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "No data");
        } catch (Exception e) {
            throw new ServiceException(EXPECTATION_FAILED.getErrorCode(), "data not retrieved");
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
        try {
            projectRepo.deleteById(projectId);
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid ID");
        }
        return " Project details deleted successfully";
    }

    @Transactional
    @Override
    public String insertImage(Optional<MultipartFile> image, int projectId) {
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
        return "Project Image Inserted Successfully";
    }

}

