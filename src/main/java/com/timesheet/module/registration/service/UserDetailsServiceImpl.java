package com.timesheet.module.registration.service;

import com.timesheet.module.registration.entity.Registration;
import com.timesheet.module.registration.entity.UserDetailsGroup;
import com.timesheet.module.registration.repo.RegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RegistrationRepo registrationRepo;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Registration registration = registrationRepo.findByLoginId(loginId);
        return new UserDetailsGroup(registration);
    }
}