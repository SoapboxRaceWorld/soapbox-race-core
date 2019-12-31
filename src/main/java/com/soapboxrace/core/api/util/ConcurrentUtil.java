/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2019.
 */

package com.soapboxrace.core.api.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentUtil {
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(24);
}
