/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api.util;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BuildInfo {
    private static String branch;
    private static String commitID;
    private static String longCommitID;
    private static String time;
    private static String version;

    static {
        try (InputStream gitPropertiesJson = getGitPropertiesStream();
             InputStreamReader reader = new InputStreamReader(gitPropertiesJson)) {
            GitStateInfo gitStateInfo = new Gson().fromJson(reader, GitStateInfo.class);
            branch = gitStateInfo.branch;
            commitID = gitStateInfo.commitIdAbbreviated;
            longCommitID = gitStateInfo.commitId;
            time = gitStateInfo.commitTime;
            version = gitStateInfo.buildVersion;
        } catch (IOException | RuntimeException exception) {
            System.out.println(exception.getMessage());
            branch = commitID = longCommitID = time = version = "unknown";
        }
    }

    private static InputStream getGitPropertiesStream() {
        ClassLoader classLoader = BuildInfo.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("git.properties");

        if (inputStream == null) {
            throw new RuntimeException("git.properties resource was not found!");
        }

        return inputStream;
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

    public static String getVersion() {
        return version.trim();
    }
}
