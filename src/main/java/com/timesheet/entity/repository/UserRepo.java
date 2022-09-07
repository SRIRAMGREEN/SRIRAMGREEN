package com.timesheet.entity.repository;

import com.timesheet.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {


    User findByLoginIdAndPassword(String loginId, String password);
}
