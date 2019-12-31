/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2019.
 */

package com.soapboxrace.core.api.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class BuildInfo {
    private static boolean loadedInfo = false;
    private static String branch = "";
    private static String commitID = "";
    private static String longCommitID = "";
    private static String time = "";

    public static void load() {
        if (!loadedInfo) {
            String data = ResourceUtils.getResourceFileAsString("project-info.properties");
            Properties properties = new Properties();
            try {
                properties.load(new StringReader(data));

                branch = properties.getProperty("branch");
                commitID = properties.getProperty("commitID");
                longCommitID = properties.getProperty("longCommitID");
                time = properties.getProperty("time");
                loadedInfo = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getBranch() {
        return branch.trim();
    }

    public static String getCommitID() {
        return commitID.trim();
    }

    public static String getLongCommitID() {
        return longCommitID.trim();
    }

    public static String getTime() {
        return time.trim();
    }
}
