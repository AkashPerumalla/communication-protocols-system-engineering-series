package com.sky2dev.day21.dto;

import java.util.List;

public record ErrorResponse(
        String errorCode,
        List<ErrorDetail> details
) {
}
