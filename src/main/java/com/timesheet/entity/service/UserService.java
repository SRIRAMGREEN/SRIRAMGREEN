package com.timesheet.entity.service;

import com.timesheet.entity.model.dto.UserDto;
import com.timesheet.entity.model.User;
import com.timesheet.entity.registration.entity.Registration;

public interface UserService {

    UserDto addUser(User user);
    UserDto updateUser(User user);
    void deleteUser(int id);
    UserDto getUserData(int id);
    void addEntryToUser(Registration registration);

}
