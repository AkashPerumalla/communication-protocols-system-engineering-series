package com.sky2dev.day20.dto;

import jakarta.validation.constraints.NotBlank;

public record ReportGenerateRequest(@NotBlank String reportType) {
}
