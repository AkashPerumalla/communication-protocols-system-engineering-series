package com.sky2dev.day19.controller;

import com.sky2dev.day19.dto.ApiResponse;
import com.sky2dev.day19.dto.LinkBudgetCalculationRequest;
import com.sky2dev.day19.dto.LinkBudgetDto;
import com.sky2dev.day19.service.LinkBudgetService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/link-budget")
@RequiredArgsConstructor
public class LinkBudgetController {

    private final LinkBudgetService linkBudgetService;

    @GetMapping
    public ApiResponse<List<LinkBudgetDto>> list() {
        return new ApiResponse<>("LINK BUDGET CALCULATED", "Link budget list retrieved", linkBudgetService.getAllBudgets());
    }

    @PostMapping("/calculate")
    public ApiResponse<LinkBudgetDto> calculate(@Valid @RequestBody LinkBudgetCalculationRequest request) {
        return new ApiResponse<>("LINK BUDGET CALCULATED", "Link budget calculated", linkBudgetService.calculateAndPersist(request));
    }
}
