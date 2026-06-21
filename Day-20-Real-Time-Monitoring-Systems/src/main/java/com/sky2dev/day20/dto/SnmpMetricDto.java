package com.sky2dev.day20.dto;

import java.time.Instant;

public record SnmpMetricDto(String agentId, String oid, String name, double value, Instant timestamp) {
}
