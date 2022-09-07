package com.timesheet.entity.service.serviceimpl;

import com.timesheet.entity.model.dto.UserDto;
import com.timesheet.entity.utils.exceptions.ServiceException;
import com.timesheet.entity.model.User;
import com.timesheet.entity.registration.entity.Registration;
import com.timesheet.entity.repository.UserRepo;
import com.timesheet.entity.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import static com.timesheet.entity.utils.TimeSheetErrorCodes.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepo userRepository;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDto addUser(User user) {

        logger.info("UserServiceImpl || addUserDetails ||Adding the User Details");
        try {
            userRepository.save(user);
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return userDto;
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Invalid data");
        }

    }

    @Override
    public UserDto updateUser(User user) {
        try {
            Optional<User> userdata = userRepository.findById(user.getId());
            if (userdata.isPresent()) {
                logger.info("UserServiceImpl || updateData || Updating the user details");
//                BeanUtils.copyProperties(user, userdata.get(), NullPropertyName.getNullPropertyNames(user));
                userRepository.save(userdata.get());
                UserDto userDto = modelMapper.map(userdata, UserDto.class);
                return userDto;
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
    public void deleteUser(int id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                logger.info("SkillSeekerServiceImpl || deleteData || Deleting the SkillSeeker id: {}", id);
                userRepository.deleteById(id);
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
    public UserDto getUserData(int id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                UserDto userDto = new UserDto();
                logger.info("SkillSeekerServiceImpl || getuserData || Updating the user Info");
                modelMapper.map(user.get(), userDto);
//                BeanUtils.copyProperties(user.get(), userDto);
                return userDto;
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
    public void addEntryToUser(Registration registration) {
        User user = new User();
        user.setEmail(registration.getEmail());
        user.setEmployeeName(registration.getEmployeeName());
        user.setLoginId(registration.getLoginId());
        user.setDepartment(registration.getDepartment());
        if (null != registration.getEmail()) {
            user.setEmail(registration.getEmail() + " " + registration.getEmployeeName());
        } else {
            user.setEmail(registration.getEmail());
        }
        //copy details from reg to user
        userRepository.save(user);
    }
}