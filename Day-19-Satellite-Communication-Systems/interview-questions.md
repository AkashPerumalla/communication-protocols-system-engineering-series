# Day-19 Interview Questions - Satellite Communication Systems

## Beginner Level

1. Question: What is the role of a Satellite NOC in VSAT operations?
Answer: It monitors links, tracks alarms, and coordinates corrective actions.
Explanation: The NOC is the operational control center for service assurance and restoration.

2. Question: What is a transponder in satellite communication?
Answer: A transponder receives uplink signals, shifts frequency, amplifies, and retransmits downlink signals.
Explanation: It acts as a bent-pipe RF relay in most GEO communications systems.

3. Question: What does uplink mean in SatCom?
Answer: Uplink is transmission from earth station to satellite.
Explanation: The uplink usually uses a higher paired frequency than downlink.

4. Question: What does downlink mean in SatCom?
Answer: Downlink is transmission from satellite to earth station.
Explanation: Remote VSAT terminals receive downlink carriers from the satellite footprint.

5. Question: What is EIRP?
Answer: Effective isotropic radiated power is the transmit power plus antenna gain minus losses.
Explanation: EIRP indicates radiated power toward the satellite direction.

6. Question: What is C/N?
Answer: Carrier-to-noise ratio measures signal quality over thermal noise.
Explanation: Low C/N degrades demodulation and can increase BER.

7. Question: What is Eb/No?
Answer: It is energy per bit over noise density.
Explanation: Eb/No links physical layer quality to coding and modulation performance.

8. Question: Why is BER important?
Answer: BER indicates bit integrity over the satellite link.
Explanation: Rising BER reflects impairments such as fading, interference, or lock instability.

9. Question: What is lock status in a modem?
Answer: It indicates whether the receiver is synchronized with the carrier.
Explanation: Loss of lock means traffic interruption risk is high.

10. Question: What is a VSAT hub-spoke topology?
Answer: A central hub communicates with multiple remote VSAT sites through satellite links.
Explanation: It is common in enterprise and telecom private satellite networks.

11. Question: Why are guard bands required in frequency planning?
Answer: Guard bands prevent adjacent-channel interference.
Explanation: They create spectral spacing between neighboring channels.

12. Question: What does link margin represent?
Answer: It is the difference between available C/N and required C/N.
Explanation: Positive margin indicates robust performance under nominal conditions.

13. Question: What does a CRITICAL alarm mean in NOC operations?
Answer: It indicates severe impact requiring immediate operator action.
Explanation: Typical examples include lock loss and very low C/N.

14. Question: What is the first action after receiving a major SatCom alarm?
Answer: Validate telemetry and isolate affected link components.
Explanation: Early triage prevents unnecessary field dispatches.

15. Question: Why use deterministic seed data in training platforms?
Answer: It ensures reproducible outputs and stable testing.
Explanation: Determinism is essential for CI validation and exercise consistency.

## Intermediate Level

16. Question: How is free-space path loss related to range and frequency?
Answer: Path loss increases with both distance and frequency.
Explanation: The formula uses logarithmic terms for frequency and slant range.

17. Question: How does rain fade affect satellite links?
Answer: It attenuates signal power, reducing C/N and Eb/No.
Explanation: Ku/Ka bands are especially sensitive to precipitation attenuation.

18. Question: What is the practical relation between low Rx power and BER increase?
Answer: Lower Rx power generally reduces SNR and increases BER.
Explanation: This is a common diagnostic pattern for alignment or weather impairments.

19. Question: When would you suspect antenna misalignment?
Answer: When Rx power drops and BER rises without clear upstream congestion.
Explanation: Mispointing reduces received carrier strength and demodulation quality.

20. Question: What operations data should a SatCom dashboard include?
Answer: Active links, alarm counts, critical alarms, availability, and average quality KPIs.
Explanation: These allow rapid situational awareness and prioritization.

21. Question: Why keep alarm severity and alarm status separate?
Answer: Severity describes impact, status describes lifecycle state.
Explanation: A CRITICAL alarm can be OPEN, ACKNOWLEDGED, or RESOLVED.

22. Question: How can a NOC differentiate warning versus major RF degradation?
Answer: By threshold bands across C/N, Eb/No, BER, and lock state.
Explanation: Multi-metric thresholds reduce false positives.

23. Question: What does an OPEN alarm imply operationally?
Answer: The fault is active and unresolved.
Explanation: Open alarms should be included in live dashboard counts.

24. Question: Why use DTOs in an enterprise Spring Boot API?
Answer: DTOs decouple entity persistence structure from API contracts.
Explanation: This improves versioning safety and data exposure control.

25. Question: What is the benefit of global exception handling in NOC APIs?
Answer: It standardizes error responses and simplifies client behavior.
Explanation: Consistent error schemas improve automation reliability.

26. Question: Why validate POST payloads with annotations?
Answer: It protects service logic from invalid engineering inputs.
Explanation: Invalid ranges in power, noise, or frequency can invalidate calculations.

27. Question: Why should frequency planning be persisted?
Answer: Persisted plans enable auditability and rollback analysis.
Explanation: NOC teams need history for change management and interference investigations.

28. Question: How do you compute link availability from status records?
Answer: Divide active/degraded operational links by total links and convert to percent.
Explanation: This KPI tracks service continuity at a network level.

29. Question: Why include timestamped link metrics?
Answer: Time correlation is required for trend analysis and incident reconstruction.
Explanation: It allows before-and-after assessment for mitigation actions.

30. Question: What is the value of simulated alarm injection endpoints?
Answer: They support controlled drills and validation of workflow automation.
Explanation: NOC readiness improves with repeatable incident simulation.

31. Question: How do modulation changes influence throughput and margin?
Answer: Higher-order modulation improves throughput but usually requires better C/N.
Explanation: Adaptive modulation balances capacity and resilience.

32. Question: Why use H2 in an educational enterprise simulation?
Answer: It enables self-contained reproducible environments without external DB dependencies.
Explanation: This shortens onboarding and CI execution time.

33. Question: How does G/T affect downlink quality?
Answer: Higher G/T improves receiver sensitivity and C/N performance.
Explanation: It combines receive gain and system noise temperature behavior.

34. Question: What is the operational effect of intermittent lock?
Answer: It causes burst errors, jitter, and unstable traffic quality.
Explanation: It often precedes full lock loss if untreated.

35. Question: Why should NOC systems link tickets to alarms?
Answer: It preserves traceability from detection to corrective action.
Explanation: This is important for SLA audits and post-incident reviews.

## Advanced Level

36. Question: How do you design deterministic yet realistic satellite simulations?
Answer: Use fixed seed datasets with representative healthy, warning, and critical profiles.
Explanation: Deterministic values allow exact test assertions while retaining engineering realism.

37. Question: What are key tradeoffs in simplified link-budget formulas?
Answer: Simplicity improves learnability but omits atmospheric, polarization, and implementation nuances.
Explanation: Production tools add rain, scintillation, and availability target modeling.

38. Question: How can alarm storms be reduced in SatCom NOC systems?
Answer: Apply deduplication, suppression windows, and correlation rules.
Explanation: Grouping related symptoms prevents operator overload.

39. Question: Why is RCA rule chaining useful in telecom operations?
Answer: It converts multi-metric symptoms into probable causes with actionable next steps.
Explanation: Structured RCA shortens mean time to repair.

40. Question: What design pattern supports maintainable threshold rules?
Answer: Use rule objects or policy services separate from transport controllers.
Explanation: This allows rule updates without API contract changes.

41. Question: How should API marker strings be used in automation?
Answer: Markers should be stable, deterministic, and tied to domain outcomes.
Explanation: Test scripts can validate behavior with simple grep checks.

42. Question: What failure mode occurs if guard bands are insufficient?
Answer: Adjacent-channel leakage and interference increase error rates.
Explanation: This may appear as BER growth and C/N erosion.

43. Question: How would you extend this platform for multi-beam satellites?
Answer: Add beam entities, beam assignment rules, and per-beam capacity counters.
Explanation: Multi-beam constraints are central for HTS optimization.

44. Question: How would you integrate modem SNMP telemetry into this model?
Answer: Add collectors that map OIDs to normalized LinkMetric updates.
Explanation: This bridges protocol-level data with NOC service logic.

45. Question: What consistency guarantees are needed for alarm-ticket workflows?
Answer: Alarm creation and ticket linkage should be transactional.
Explanation: Partial writes can break incident traceability.

46. Question: How can troubleshooting recommendations become adaptive?
Answer: Combine rule-based logic with historical outcome scoring.
Explanation: Recommended actions can be ranked by past resolution success.

47. Question: What indices matter for performance at scale?
Answer: Index link_id and timestamp for metrics, severity/status for alarms, and source for RCA lookups.
Explanation: These support common dashboard and incident query patterns.

48. Question: How would you support SLA-aware prioritization?
Answer: Add customer/site SLA tiers and factor them into alarm escalation.
Explanation: Equal-severity alarms may need different response urgency.

49. Question: How do you make NOC dashboards actionable rather than descriptive?
Answer: Add threshold-based highlights, trend deltas, and suggested operator actions.
Explanation: Operators need decision support, not just data tables.

50. Question: How can capacity planning leverage this data model?
Answer: Use transponder occupancy, throughput trends, and margin headroom analytics.
Explanation: It supports proactive channel expansion and load redistribution.

51. Question: What is the risk of relying only on average BER in dashboards?
Answer: Averages can hide short severe bursts impacting real-time services.
Explanation: Percentile and worst-case indicators should complement mean values.

52. Question: How would you model protocol-level failures beyond lock status?
Answer: Add modem sync states, FEC mode transitions, and frame loss counters.
Explanation: This improves fault isolation between RF and modem layers.

53. Question: Why should troubleshooting actions include verification steps?
Answer: Corrective actions must be validated against post-action telemetry.
Explanation: Closed-loop verification prevents unresolved recurring incidents.

54. Question: How do you prevent invalid engineering values in production APIs?
Answer: Enforce range validation and domain constraints at request boundaries.
Explanation: This guards analytical integrity and operational trust.

55. Question: How can this architecture evolve toward real-time streaming analytics?
Answer: Introduce event streaming ingestion and asynchronous KPI aggregators.
Explanation: This supports high-frequency telemetry without blocking request paths.
