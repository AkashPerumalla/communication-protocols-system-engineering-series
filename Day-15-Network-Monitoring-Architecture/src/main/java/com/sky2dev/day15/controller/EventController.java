package com.sky2dev.day15.controller;

import com.sky2dev.day15.dto.TrapEventResponse;
import com.sky2dev.day15.simulator.TrapProcessor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final TrapProcessor trapProcessor;

    @GetMapping
    public List<TrapEventResponse> getEvents() {
        return trapProcessor.findRecentEvents().stream().map(TrapEventResponse::from).toList();
    }
}
