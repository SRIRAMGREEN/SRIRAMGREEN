package com.timesheet.entity.registration.repo;

import com.timesheet.entity.registration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    Integer findById(int id);
    User findByVerificationCode(String code);

    User findByLoginIdAndPassword(String loginId, String password);
}
