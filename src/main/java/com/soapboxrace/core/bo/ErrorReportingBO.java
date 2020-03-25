package com.soapboxrace.core.bo;

import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;

import javax.annotation.PostConstruct;
import javax.ejb.*;

@Startup
@Singleton
public class ErrorReportingBO {

    private SentryClient sentryClient;

    @EJB
    private ParameterBO parameterBO;

    private boolean enabled;

    @PostConstruct
    public void init() {
        System.out.println("ErrorReportingBO is starting");
        this.enabled = parameterBO.getBoolParam("ENABLE_SENTRY_REPORTING");

        if (this.enabled) {
            this.sentryClient = SentryClientFactory.sentryClient(parameterBO.getStrParam("SENTRY_DSN"));
        }

        System.out.println("ErrorReportingBO has started");
    }

    @Asynchronous
    @Lock(LockType.READ)
    public void sendException(Exception exception) {
        if (this.enabled) {
            this.sentryClient.sendException(exception);
        }
    }
}
