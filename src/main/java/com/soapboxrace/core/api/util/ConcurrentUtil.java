package com.soapboxrace.core.api.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentUtil
{
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(24);
}
