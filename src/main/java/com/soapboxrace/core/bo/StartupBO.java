package com.soapboxrace.core.bo;

import com.soapboxrace.core.api.util.BuildInfo;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class StartupBO {
    @PostConstruct
    public void init() {
        BuildInfo.load();

        String template = "StartupBO: Server running on code version %s (%s) - branch %s";

        System.out.println(String.format(template, BuildInfo.getCommitID(), BuildInfo.getLongCommitID(), BuildInfo.getBranch()));

//        System.out.println("StartupBO: Server running on code version " + BuildInfo.getCommitID() + " (" + BuildInfo.getLongCommitID() + ")");
    }
}
