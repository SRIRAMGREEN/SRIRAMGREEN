package com.timesheet.module.verificationmail.repo;//package com.flexcub.resourceplanning.verificationmail.repository;

import com.timesheet.module.verificationmail.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

    VerificationToken findByToken(String token);

    VerificationToken removeByToken(String token);
}

