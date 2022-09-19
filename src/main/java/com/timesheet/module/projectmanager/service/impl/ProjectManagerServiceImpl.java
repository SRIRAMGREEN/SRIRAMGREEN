package com.timesheet.module.projectmanager.service.impl;

import com.timesheet.module.SuperAdmin.dto.ProjectManagerDto2;
import com.timesheet.module.projectmanager.entity.ProjectManager;
import com.timesheet.module.projectmanager.entity.dto.ProjectManagerDto;
import com.timesheet.module.projectmanager.repository.ProjectManagerRepo;
import com.timesheet.module.projectmanager.service.ProjectManagerService;
import com.timesheet.module.registration.entity.Registration;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timesheet.module.utils.TimeSheetErrorCodes.*;

@Service
public class ProjectManagerServiceImpl implements ProjectManagerService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ProjectManagerRepo projectManagerRepo;

    Logger logger = LoggerFactory.getLogger(ProjectManagerServiceImpl.class);

    @Override
    public ProjectManagerDto2 addProjectManager(ProjectManager projectManager) {
        logger.info("UserServiceImpl || addUserDetails ||Adding the User Details");
        try {
            projectManagerRepo.save(projectManager);
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            ProjectManagerDto2 projectManagerDto = modelMapper.map(projectManager, ProjectManagerDto2.class);
            return projectManagerDto;
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Invalid data");
        }

    }

    @Override
    public ProjectManagerDto getProjectManagerDetails(int projectManagerId) {
        try {
            Optional<ProjectManager> projectManager = projectManagerRepo.findById(projectManagerId);
            if (projectManager.isPresent()) {
                logger.info("EmployeeServiceImpl || get-EmployeeData || Updating the Employee Info");
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                ProjectManagerDto projectManagerDto = modelMapper.map(projectManager.get(), ProjectManagerDto.class);
                return projectManagerDto;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid request/Id not found");
        } catch (Exception e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid data");
        }
    }

    @Override
    public List<ProjectManagerDto> getAllProjectManagerDetails() {
        logger.info("ProjectManagerServiceImpl || getAllClientDetails || Get all project manager from the ClientEntity");
        try {
            Optional<List<ProjectManager>> projectManagers = Optional.ofNullable(projectManagerRepo.findAll());
            List<ProjectManagerDto> projectManagerDtoList = new ArrayList<>();
            if (!projectManagers.get().isEmpty()) {
                for (ProjectManager projectManager : projectManagers.get()) {
                    ProjectManagerDto projectManagerDto = modelMapper.map(projectManager, ProjectManagerDto.class);
                    projectManagerDtoList.add(projectManagerDto);
                }
                return projectManagerDtoList;
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

    @Transactional
    @Override
    public ProjectManagerDto updateProjectManager(ProjectManager projectManager) {
        try {
            ProjectManagerDto projectManagerDto = new ProjectManagerDto();
            Optional<ProjectManager> projectManager1 = projectManagerRepo.findById(projectManager.getId());
            if (projectManager1.isPresent()) {
                BeanUtils.copyProperties(projectManager, projectManager1.get(), NullPropertyName.getNullPropertyNames(projectManager));
                logger.info("ProjectManagerServiceImpl  || updateProjectManager || DataSet was updated in ProjectManager=={}", projectManager);
                ProjectManager projectManager2 = projectManagerRepo.save(projectManager1.get());
                BeanUtils.copyProperties(projectManager2, projectManagerDto, NullPropertyName.getNullPropertyNames(projectManager2));
                return projectManagerDto;
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
    public String deleteProjectManager(int projectManagerId) {
        try {
            Optional<ProjectManager> projectManager = projectManagerRepo.findById(projectManagerId);
            if (projectManager.isPresent()) {
                logger.info("ProjectManagerServiceImpl || deleteData || Deleting the ProjectManager id: {}", projectManagerId);
                projectManagerRepo.deleteById(projectManagerId);
            } else {
                throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Data not found against the id");
            }
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Invalid data");
        }
        return "ProjectManager Details Deleted Successfully";
    }

    @Override
    public void addEntryToProjectManager(Registration registration) {
        ProjectManager projectManager = new ProjectManager();
        registration.getRoles().getRolesId();
        projectManager.setEmailId(registration.getEmailId());
        projectManager.setProjectManagerName(registration.getEmployeeName());
        projectManager.setLoginId(registration.getLoginId());
        projectManager.setDepartment(registration.getDepartment());
        if (null != registration.getEmailId()) {
            projectManager.setEmailId(registration.getEmailId() + " " + registration.getEmployeeName());
        } else {
            projectManager.setEmailId(registration.getEmailId());
        }
        //copy details from reg to user
        projectManagerRepo.save(projectManager);
    }
}