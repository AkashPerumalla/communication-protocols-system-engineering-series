package com.sky2dev.day19.dto;

import java.util.List;

public record VsatTopologyDto(
        String hub,
        List<String> spokes,
        Integer totalSites,
        String topologyType
) {
}
