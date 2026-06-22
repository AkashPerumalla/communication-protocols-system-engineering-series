package com.sky2dev.day21.repository;

import com.sky2dev.day21.entity.Device;
import com.sky2dev.day21.entity.DeviceStatus;
import com.sky2dev.day21.entity.DeviceType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByHostnameIgnoreCase(String hostname);

    Optional<Device> findByIpAddress(String ipAddress);

    Page<Device> findAllByStatus(DeviceStatus status, Pageable pageable);

    Page<Device> findAllByDeviceType(DeviceType deviceType, Pageable pageable);

    Page<Device> findAllByLocationIgnoreCaseContaining(String location, Pageable pageable);

    long countByStatus(DeviceStatus status);

    long countByDeviceType(DeviceType deviceType);

    boolean existsByHostnameIgnoreCase(String hostname);

    boolean existsByIpAddress(String ipAddress);

    @Query("""
            select d from Device d
            where (:keyword is null or lower(d.hostname) like lower(concat('%', :keyword, '%'))
                or lower(d.ipAddress) like lower(concat('%', :keyword, '%'))
                or lower(d.vendor) like lower(concat('%', :keyword, '%'))
                or lower(d.model) like lower(concat('%', :keyword, '%'))
                or lower(d.location) like lower(concat('%', :keyword, '%')))
              and (:status is null or d.status = :status)
              and (:deviceType is null or d.deviceType = :deviceType)
              and (:location is null or lower(d.location) like lower(concat('%', :location, '%')))
            """)
    Page<Device> search(
            @Param("keyword") String keyword,
            @Param("status") DeviceStatus status,
            @Param("deviceType") DeviceType deviceType,
            @Param("location") String location,
            Pageable pageable);

    @Query("select distinct d.location from Device d order by d.location asc")
    List<String> findDistinctLocations();
}
