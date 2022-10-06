package com.timesheet.module.registration.repo;

import com.timesheet.module.registration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRepo extends JpaRepository<Registration, Integer> {
    Registration findByEmailId(String emailId);


    Optional<Registration> findByLoginId(String loginId);
    Registration findByToken(String token);

    @Query(value ="SELECT * FROM registration Where employee_name =?;", nativeQuery = true)
    Registration findByName(String name);
}
