package com.sky2dev.day15.alert;

import com.sky2dev.day15.entity.AlertSeverity;

public record AlertCandidate(String ruleName, AlertSeverity severity, String message) {
}
