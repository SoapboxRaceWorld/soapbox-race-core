/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api.util;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BuildInfo {
    private static boolean loadedInfo = false;
    private static String branch = "";
    private static String commitID = "";
    private static String longCommitID = "";
    private static String time = "";

    public static void load() {
        if (!loadedInfo) {
            String gitPropertiesJson = readGitProperties();
            System.out.println(gitPropertiesJson);
            GitStateInfo gitStateInfo = new Gson().fromJson(gitPropertiesJson, GitStateInfo.class);
            branch = gitStateInfo.branch;
            commitID = gitStateInfo.commitIdAbbreviated;
            longCommitID = gitStateInfo.commitId;
            time = gitStateInfo.commitTime;

            loadedInfo = true;
        }
    }


    private static String readGitProperties() {
        ClassLoader classLoader = BuildInfo.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("git.properties");
        try {
            return readFromInputStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read git.properties", e);
        }
    }

    private static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
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
