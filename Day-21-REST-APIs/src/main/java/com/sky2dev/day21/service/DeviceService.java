package com.sky2dev.day21.service;

import com.sky2dev.day21.dto.DeviceRequest;
import com.sky2dev.day21.dto.DeviceResponse;
import com.sky2dev.day21.dto.PageResponse;
import com.sky2dev.day21.entity.Device;
import com.sky2dev.day21.entity.DeviceStatus;
import com.sky2dev.day21.entity.DeviceType;
import com.sky2dev.day21.mapper.DeviceMapper;
import com.sky2dev.day21.repository.DeviceMetricRepository;
import com.sky2dev.day21.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMetricRepository deviceMetricRepository;
    private final DeviceMapper deviceMapper;

    public DeviceResponse createDevice(DeviceRequest request) {
        validateUniqueness(request, null);
        Device device = deviceMapper.toEntity(request);
        return deviceMapper.toResponse(deviceRepository.save(device));
    }

    public DeviceResponse updateDevice(Long id, DeviceRequest request) {
        Device device = findEntity(id);
        validateUniqueness(request, id);
        deviceMapper.updateEntity(device, request);
        return deviceMapper.toResponse(deviceRepository.save(device));
    }

    public void deleteDevice(Long id) {
        Device device = findEntity(id);
        deviceMetricRepository.deleteByDeviceId(id);
        deviceRepository.delete(device);
    }

    @Transactional(readOnly = true)
    public DeviceResponse getDevice(Long id) {
        return deviceMapper.toResponse(findEntity(id));
    }

    @Transactional(readOnly = true)
    public PageResponse<DeviceResponse> getAllDevices(int page, int size, String sortBy, String direction) {
        Page<Device> result = deviceRepository.findAll(buildPageable(page, size, sortBy, direction));
        return toPageResponse(result);
    }

    @Transactional(readOnly = true)
    public PageResponse<DeviceResponse> searchDevices(String keyword, DeviceStatus status, DeviceType type, String location, int page, int size, String sortBy, String direction) {
        Page<Device> result = deviceRepository.search(normalize(keyword), status, type, normalize(location), buildPageable(page, size, sortBy, direction));
        return toPageResponse(result);
    }

    @Transactional(readOnly = true)
    public PageResponse<DeviceResponse> findByStatus(DeviceStatus status, int page, int size, String sortBy, String direction) {
        return toPageResponse(deviceRepository.findAllByStatus(status, buildPageable(page, size, sortBy, direction)));
    }

    @Transactional(readOnly = true)
    public PageResponse<DeviceResponse> findByType(DeviceType type, int page, int size, String sortBy, String direction) {
        return toPageResponse(deviceRepository.findAllByDeviceType(type, buildPageable(page, size, sortBy, direction)));
    }

    @Transactional(readOnly = true)
    public PageResponse<DeviceResponse> findByLocation(String location, int page, int size, String sortBy, String direction) {
        return toPageResponse(deviceRepository.findAllByLocationIgnoreCaseContaining(location, buildPageable(page, size, sortBy, direction)));
    }

    @Transactional(readOnly = true)
    public List<String> getLocations() {
        return deviceRepository.findDistinctLocations();
    }

    @Transactional(readOnly = true)
    public List<Device> getAllManagedEntities() {
        return deviceRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Transactional(readOnly = true)
    public Device findEntity(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Device not found for id " + id));
    }

    private void validateUniqueness(DeviceRequest request, Long currentId) {
        deviceRepository.findByHostnameIgnoreCase(request.hostname())
                .filter(device -> !device.getId().equals(currentId))
                .ifPresent(device -> {
                    throw new IllegalArgumentException("hostname already exists: " + request.hostname());
                });

        deviceRepository.findByIpAddress(request.ipAddress())
                .filter(device -> !device.getId().equals(currentId))
                .ifPresent(device -> {
                    throw new IllegalArgumentException("ipAddress already exists: " + request.ipAddress());
                });
    }

    private Pageable buildPageable(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by("desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC,
                normalizeSort(sortBy));
        return PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 100), sort);
    }

    private String normalizeSort(String sortBy) {
        String candidate = sortBy == null ? "hostname" : sortBy;
        return switch (candidate) {
            case "id", "hostname", "ipAddress", "deviceType", "vendor", "model", "location", "status", "createdAt", "updatedAt" -> candidate;
            default -> "hostname";
        };
    }

    private String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private PageResponse<DeviceResponse> toPageResponse(Page<Device> page) {
        return new PageResponse<>(
                page.stream().map(deviceMapper::toResponse).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
    }
}
