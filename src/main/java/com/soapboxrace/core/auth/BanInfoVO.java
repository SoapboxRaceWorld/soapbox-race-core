/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.auth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class BanInfoVO {
    String reason;
    LocalDateTime endsAt;

    public BanInfoVO(String reason, LocalDateTime endsAt) {
        this.reason = reason;
        this.endsAt = endsAt;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public String getFormattedEndsAt() {
        if (endsAt == null) return null;
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
            .withZone(ZoneId.systemDefault())
            .format(endsAt);
    }

    public String getBanMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Your account has been banned");
        if (endsAt != null) {
            sb.append(" until ");
            sb.append(getFormattedEndsAt());
        }
        if (reason != null) {
            sb.append('\n');
            sb.append("Reason: ");
            sb.append(reason);
        }
        return sb.toString();
    }
}