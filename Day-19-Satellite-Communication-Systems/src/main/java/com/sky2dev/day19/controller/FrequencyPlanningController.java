package com.sky2dev.day19.controller;

import com.sky2dev.day19.dto.ApiResponse;
import com.sky2dev.day19.dto.FrequencyPlanDto;
import com.sky2dev.day19.dto.FrequencyPlanGenerateRequest;
import com.sky2dev.day19.service.FrequencyPlanningService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/frequency-plan")
@RequiredArgsConstructor
public class FrequencyPlanningController {

    private final FrequencyPlanningService frequencyPlanningService;

    @GetMapping
    public ApiResponse<List<FrequencyPlanDto>> list() {
        return new ApiResponse<>("FREQUENCY PLAN GENERATED", "Frequency plans listed", frequencyPlanningService.listPlans());
    }

    @PostMapping("/generate")
    public ApiResponse<List<FrequencyPlanDto>> generate(@Valid @RequestBody FrequencyPlanGenerateRequest request) {
        List<FrequencyPlanDto> generated = frequencyPlanningService.generateFrequencyPlan(request);
        boolean valid = frequencyPlanningService.validateGuardBands();
        return new ApiResponse<>(
                "FREQUENCY PLAN GENERATED",
                valid ? "Frequency plan generated with valid guard bands" : "Frequency plan generated with guard band overlap",
                generated
        );
    }
}
