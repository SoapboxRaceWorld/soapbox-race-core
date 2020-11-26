package com.soapboxrace.core.bo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

public class MiniProfilingTimer {
    private final Stack<ProfileEntry> entries = new Stack<>();
    private final Logger logger;

    public MiniProfilingTimer(String timerName) {
        this.logger = LoggerFactory.getLogger(timerName);
    }

    public void begin(String label) {
        entries.push(new ProfileEntry(label));
    }

    public void end() {
        ProfileEntry entry = entries.pop();
        Long diff = System.currentTimeMillis() - entry.started;
        logger.info("{} took {}ms to finish", entry.label, diff);
    }

    private static class ProfileEntry {
        private final String label;
        private final Long started;

        public ProfileEntry(String label) {
            this.label = label;
            this.started = System.currentTimeMillis();
        }

        public String getLabel() {
            return label;
        }

        public Long getStarted() {
            return started;
        }
    }
}
