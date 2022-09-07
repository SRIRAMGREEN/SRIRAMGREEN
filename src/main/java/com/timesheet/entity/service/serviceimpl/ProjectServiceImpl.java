package com.timesheet.entity.service.serviceimpl;

import com.timesheet.entity.model.dto.ProjectDto;
import com.timesheet.entity.utils.exceptions.ServiceException;
import com.timesheet.entity.model.Project;
import com.timesheet.entity.repository.ProjectRepo;
import com.timesheet.entity.service.ProjectService;
import com.timesheet.entity.utils.NullPropertyName;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.timesheet.entity.utils.TimeSheetErrorCodes.*;
import static com.timesheet.entity.utils.TimesheetConstants.IMAGE_REGEX;


@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepo projectRepo;

    @Autowired
    ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Override
    public ProjectDto insertProject(Project project) {
        ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
        try {
            logger.info("SkillSeekerProjectServiceImpl || insertData || Inserting the SeekerProject list: {}", projectDto);
            projectRepo.save(project);
            return projectDto;
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode());
        }
    }

    @Override
    public ProjectDto getProjectDetails(int projectId) {
        try {
            Optional<Project> project = projectRepo.findById(projectId);
            ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);

            if (project.isPresent()) {
                return projectDto;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid req/Id not found");
        } catch (Exception e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid req");
        }
//        try {
//            Optional<Project> projectData = projectRepo.findByProjectId(projectId);
//            ProjectDto projectDto = modelMapper.map(projectData,ProjectDto.class);
//            if (projectData.isPresent()) {
////                BeanUtils.copyProperties(projectData, projectDto);
//                return projectDto;
//            } else {
//                throw new ServiceException(DATA_NOT_FOUND.getErrorCode());
//            }
//        } catch (NullPointerException e) {
//            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
//        }
    }

    @Override
    public ProjectDto updateProject(Project project) {
        try {
            ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
            Optional<Project> projectData = projectRepo.findById(projectDto.getProjectId());
            if (projectData.isPresent()) {
                BeanUtils.copyProperties(project, projectData.get(), NullPropertyName.getNullPropertyNames(project));
                logger.info("ProjectServiceImpl || updateData || Updating the  Project");
                Project projects = projectRepo.save(projectData.get());
                modelMapper.map(project,ProjectDto.class);
//                BeanUtils.copyProperties(projects, projectDto, NullPropertyName.getNullPropertyNames(projects));
                return projectDto;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request/Id not found");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Invalid data");
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

