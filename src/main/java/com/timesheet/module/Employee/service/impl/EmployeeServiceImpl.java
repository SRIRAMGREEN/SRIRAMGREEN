package com.timesheet.module.Employee.service.impl;

import com.timesheet.module.Employee.dto.EmployeeDto;
import com.timesheet.module.Employee.entity.Employee;
import com.timesheet.module.Employee.repository.EmployeeRepo;
import com.timesheet.module.Employee.service.EmployeeService;
import com.timesheet.module.SuperAdmin.dto.EmployeeDto2;
import com.timesheet.module.registration.entity.Registration;
import com.timesheet.module.team.entity.Team;
import com.timesheet.module.team.repository.TeamRepo;
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
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    TeamRepo teamRepo;

    @Override
    public EmployeeDto2 addEmployeeByAdmin(Employee employee) {
        logger.info("EmployeeServiceImpl || addEmployeeDetails ||Adding the User Details");
        try {
            Employee employee1 = employeeRepo.save(employee);
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            return modelMapper.map(employee1, EmployeeDto2.class);

        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid requests");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Invalid data's");
        }

    }

    @Override
    public List<EmployeeDto> getAllEmployeeDetails() {
        logger.info("ProjectServiceImpl || getAllProjectDetails || Get all project manager from the ProjectDetails");
        try {
            Optional<List<Employee>> employee = Optional.of(employeeRepo.findAll());
            List<EmployeeDto> employeeDtoList = new ArrayList<>();
            if (!employee.get().isEmpty()) {
                for (Employee employee1 : employee.get()) {
                    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                    EmployeeDto employeeDto = modelMapper.map(employee1, EmployeeDto.class);
                    employeeDtoList.add(employeeDto);
                }
                return employeeDtoList;
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
    public EmployeeDto getEmployeeData(int id) {
        try {
            Optional<Employee> employee = employeeRepo.findById(id);
            if (employee.isPresent()) {
                logger.info("EmployeeServiceImpl || get-EmployeeData || Updating the Employee Info");
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                return modelMapper.map(employee.get(), EmployeeDto.class);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid request/Id not found");
        } catch (Exception e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid data's");
        }
    }


    @Override
    @Transactional
    public List<EmployeeDto> getAllEmployeeByTeamId(int teamId) {
        logger.info("EmployeeDataServiceImpl || getAllEmployeeByTeamId || Get all employeeData from the EmployeeDataEntity");
        try {
            Optional<List<Employee>> employees = employeeRepo.findByTeamId(teamId);
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            List<EmployeeDto> employeeDtoList = new ArrayList<>();
            if (!employees.get().isEmpty()) {
                for (Employee employee : employees.get()) {
                    EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
                    employeeDtoList.add(employeeDto);
                }
                return employeeDtoList;
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
    public EmployeeDto updateEmployee(Employee employee) {
        try {
            EmployeeDto employeeDto = new EmployeeDto();
            Optional<Employee> employee1 = employeeRepo.findById(employee.getId());
            if (employee1.isPresent()) {
                BeanUtils.copyProperties(employee, employee1.get(), NullPropertyName.getNullPropertyNames(employee));
                logger.info("EmployeeServiceImpl  || updateEmployee || Data was updated Employee=={}", employee);
                Employee employee2 = employeeRepo.save(employee1.get());
                BeanUtils.copyProperties(employee2, employeeDto, NullPropertyName.getNullPropertyNames(employee2));
                return employeeDto;
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
    public EmployeeDto updateEmployeeTeam(int employeeId, int teamId) {
        try {
            Employee employee1 = employeeRepo.findById(employeeId).orElseThrow(() -> new IllegalArgumentException("employeeId not present"));
            Team team = teamRepo.findById(teamId).orElseThrow(() -> new IllegalArgumentException("teamId is not present"));
            employee1.setTeam(team);
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            logger.info("EmployeeServiceImpl  || updateEmployee || Data was updated Employee=={}", employee1);
            modelMapper.map(employee1, EmployeeDto.class);
            Employee employee2 = employeeRepo.save(employee1);
            return modelMapper.map(employee2, EmployeeDto.class);
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid data");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Data not saved");
        }
    }

    @Override
    public void deleteUser(int id) {
        try {
            Optional<Employee> employee = employeeRepo.findById(id);
            if (employee.isPresent()) {
                logger.info("EmployeeServiceImpl || deleteData || Deleting the SkillSeeker id: {}", id);
                employeeRepo.deleteById(id);
            } else {
                throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Data not found against the id");
            }
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Invalid data");
        }
    }

    @Override
    public void addEntryToEmployee(Registration registration) {
        Employee employee = new Employee();
        employee.setId(registration.getId());
        employee.setEmployeesName(registration.getEmployeeName());
        employeeRepo.save(employee);
    }
}
