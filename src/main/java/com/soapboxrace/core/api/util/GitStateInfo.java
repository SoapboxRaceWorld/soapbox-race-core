/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api.util;

import com.google.gson.annotations.SerializedName;

public class GitStateInfo {

    @SerializedName("git.branch")
    public String branch;
    @SerializedName("git.build.host")
    public String buildHost;
    @SerializedName("git.build.time")
    public String buildTime;
    @SerializedName("git.build.user.email")
    public String buildUserEmail;
    @SerializedName("git.build.user.name")
    public String buildUserName;
    @SerializedName("git.build.version")
    public String buildVersion;
    @SerializedName("git.commit.id")
    public String commitId;
    @SerializedName("git.commit.id.abbrev")
    public String commitIdAbbreviated;
    @SerializedName("git.commit.id.describe")
    public String commitIdDescription;
    @SerializedName("git.commit.id.describe-short")
    public String commitIdShortDescription;
    @SerializedName("git.commit.message.full")
    public String commitMessage;
    @SerializedName("git.commit.message.short")
    public String commitShortMessage;
    @SerializedName("git.commit.time")
    public String commitTime;
    @SerializedName("git.commit.user.email")
    public String commitUserEmail;
    @SerializedName("git.commit.user.name")
    public String commitUserName;
}
