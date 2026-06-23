package com.sky2dev.day22.controller;

import com.sky2dev.day22.dto.ApiResponse;
import com.sky2dev.day22.dto.ConnectedClientResponse;
import com.sky2dev.day22.service.ConnectedClientService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ConnectedClientController {

    private final ConnectedClientService connectedClientService;

    @GetMapping
    public ApiResponse<List<ConnectedClientResponse>> getClients() {
        return ApiResponse.success("CLIENT CONNECTED", "Active clients retrieved", connectedClientService.getActiveClients());
    }
}
