/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.EventDAO;
import com.soapboxrace.core.jpa.EventEntity;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Startup
public class EventRotationBO {

    @Resource
    private TimerService timerService;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private EventDAO eventDAO;

    public void setTimerService(TimerService timerService) {
        this.timerService = timerService;
    }

    @PostConstruct
    private void initialize() {
        if (parameterBO.getBoolParam("ENABLE_EVENT_ROTATION")) {
            String rotationCron = parameterBO.getStrParam("EVENT_ROTATION_SCHEDULE");

            if (rotationCron != null && !rotationCron.isEmpty()) {
                timerService.createCalendarTimer(parseCronExpressionToScheduleExpression(rotationCron));
            }
        }
    }

    @Timeout
    public void timerTimeout(Timer timer) {
        Integer rotationEventCount = parameterBO.getIntParam("EVENT_ROTATION_NUM_EVENTS");
        List<EventEntity> allEvents = eventDAO.findAllRotatable();
        SecureRandom random = new SecureRandom();

        if (rotationEventCount > 0 && rotationEventCount <= allEvents.size()) {
            for (EventEntity eventEntity : allEvents) {
                eventEntity.setIsEnabled(false);
                eventDAO.update(eventEntity);
            }

            List<EventEntity> newEvents = new ArrayList<>();
            for (int i = 0; i < rotationEventCount; i++) {
//                EventEntity eventEntity = allEvents.get(random.nextInt(allEvents.size()));
//                eventEntity.setIsEnabled(true);
//                eventDAO.update(eventEntity);
                EventEntity eventEntity;

                do {
                    eventEntity = allEvents.get(random.nextInt(allEvents.size()));
                } while (newEvents.contains(eventEntity));

                eventEntity.setIsEnabled(true);
                eventDAO.update(eventEntity);
                newEvents.add(eventEntity);

                System.out.println("EventRotationBO: event #" + (i + 1) + " in rotation is: " + eventEntity.getName());
            }

            System.out.println("EventRotationBO: done with rotation, rotated " + rotationEventCount + " event(s)");
        }
    }

    // from https://stackoverflow.com/a/48226513 - modified a bit
    private ScheduleExpression parseCronExpressionToScheduleExpression(String cronExpression) {

        if ("never".equals(cronExpression)) {
            return null;
        }

        // parsing it more or less like cron does, at least supporting same fields (+ seconds)

        final String[] parts = cronExpression.split(" ");
        final ScheduleExpression scheduleExpression;

        if (parts.length == 6) { // enriched cron with seconds
            return new ScheduleExpression()
                    .second(parts[0])
                    .minute(parts[1])
                    .hour(parts[2])
                    .dayOfMonth(parts[3])
                    .month(parts[4])
                    .dayOfWeek(parts[5]);
        }

        // cron
        return new ScheduleExpression()
                .minute(parts[0])
                .hour(parts[1])
                .dayOfMonth(parts[2])
                .month(parts[3])
                .dayOfWeek(parts[4]);
    }
}