/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.auth.verifiers;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.soapboxrace.core.auth.AuthUtil;
import com.soapboxrace.core.bo.Argon2BO;
import com.soapboxrace.core.jpa.UserEntity;

public class ModernPasswordVerifier implements PasswordVerifier {
    private Argon2BO argon2;
    private String password;

    public ModernPasswordVerifier(Argon2BO argon2, String password) {
        this.argon2 = argon2;
        this.password = password;
    }

    @Override
    public String createHash() {
        return argon2.hash(password);
    }

    @Override
    public boolean verifyHash(UserEntity user) {
        String dbHash = user.getPassword();
        boolean needsRehash = false;
        if (dbHash.length() == 40) {
            // DB has legacy hash, verify
            @SuppressWarnings("deprecation")
            String legacyHash = Hashing.sha1().hashString(password, Charsets.UTF_8).toString();
            if (!AuthUtil.stringsEqual(dbHash, legacyHash)) {
                return false;
            }
            needsRehash = true;
        } else {
            if (!argon2.verify(password, dbHash)) {
                return false;
            }
            needsRehash = argon2.needsRehash(dbHash);
        }
        if (needsRehash) {
            user.setPassword(createHash());
        }
        return true;
    }
}
