/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.auth;

import com.google.common.base.Charsets;

import java.security.MessageDigest;

public class AuthUtil {
    public static boolean stringsEqual(String a, String b) {
        byte[] ab = a.getBytes(Charsets.UTF_8);
        byte[] bb = b.getBytes(Charsets.UTF_8);
        return MessageDigest.isEqual(ab, bb);
    }
}