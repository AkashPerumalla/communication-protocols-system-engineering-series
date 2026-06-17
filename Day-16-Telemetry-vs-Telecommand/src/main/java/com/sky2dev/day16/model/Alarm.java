package com.sky2dev.day16.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alarms")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_id", nullable = false)
	private ManagedDevice device;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AlarmSeverity severity;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AlarmStatus status;

	@Column(nullable = false)
	private String metricName;

	@Column(nullable = false)
	private double metricValue;

	@Column(nullable = false)
	private double thresholdValue;

	@Column(nullable = false)
	private String message;

	@Column(nullable = false)
	private Instant triggeredAt;

	private Instant clearedAt;

	private String recoveryNote;
}
