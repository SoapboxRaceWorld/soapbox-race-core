/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.auth.verifiers;

import com.soapboxrace.core.auth.AuthUtil;
import com.soapboxrace.core.jpa.UserEntity;

public class LegacyPasswordVerifier implements PasswordVerifier {
    private String passwordHash;

    public LegacyPasswordVerifier(String hash) {
        passwordHash = hash;
    }

    public String createHash() {
        return passwordHash;
    }

    @Override
    public boolean verifyHash(UserEntity user) {
        return AuthUtil.stringsEqual(passwordHash, user.getPassword());
    }
}