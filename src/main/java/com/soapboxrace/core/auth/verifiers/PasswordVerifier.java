/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.auth.verifiers;

import com.soapboxrace.core.jpa.UserEntity;

public interface PasswordVerifier {
    String createHash();
    boolean verifyHash(UserEntity user);
}
