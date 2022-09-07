package com.timesheet.entity.registration.repo;

import com.timesheet.entity.model.User;
import com.timesheet.entity.registration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepo extends JpaRepository<Registration,Integer> {
    Registration findByEmail(String email);

    Integer findById(int id);
    Registration findByVerificationCode(String code);

    Registration findByLoginIdAndPassword(String loginId, String password);
}
