package com.soapboxrace.core.bo;

import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;

@Startup
@Singleton
public class ErrorReportingBO {

    @EJB
    private ParameterBO parameterBO;

    @Inject
    private Logger logger;

    private SentryClient sentryClient;

    @PostConstruct
    public void init() {
        if (parameterBO.getBoolParam("ENABLE_SENTRY_REPORTING")) {
            this.sentryClient = SentryClientFactory.sentryClient(parameterBO.getStrParam("SENTRY_DSN"));
            this.logger.info("Initialized error reporting system with Sentry support");
        }
    }

    @Asynchronous
    @Lock(LockType.READ)
    public void sendException(Exception exception) {
        if (this.sentryClient != null) {
            this.sentryClient.sendException(exception);
        }
    }
}
