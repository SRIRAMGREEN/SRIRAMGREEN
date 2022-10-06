package com.timesheet.module.verificationmail.service;


import com.timesheet.module.verificationmail.entity.VerificationToken;

public interface VerificationService {

    VerificationToken createVerificationToken();

    void saveSecureToken(VerificationToken token);

    VerificationToken findByToken(String token);

    void removeToken(VerificationToken token);

    void removeTokenByToken(String token);
}

