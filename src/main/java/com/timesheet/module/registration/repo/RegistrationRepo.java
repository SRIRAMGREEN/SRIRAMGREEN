package com.timesheet.module.registration.repo;

import com.timesheet.module.registration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepo extends JpaRepository<Registration, Integer> {
    Registration findByEmailId(String emailId);
    Registration findByLoginId(String loginId);
    Registration findByToken(String token);

}
