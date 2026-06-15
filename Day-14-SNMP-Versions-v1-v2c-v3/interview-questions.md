# Day 14 - Interview Questions

## SNMP Versions

1. Question: What is the practical difference between SNMPv1, SNMPv2c, and SNMPv3?
   Answer: SNMPv1 and SNMPv2c are community-based and provide no authentication or encryption, while SNMPv3 adds user-based security.
   Explanation: Version choice directly changes security posture and operational risk.

2. Question: Why is SNMPv1 still seen in labs and legacy sites?
   Answer: It is simple, widely understood, and still appears in older devices.
   Explanation: Its low overhead is outweighed by weak security in production environments.

3. Question: What improved in SNMPv2c compared with SNMPv1?
   Answer: SNMPv2c introduced GETBULK and better protocol efficiency.
   Explanation: It improved polling behavior but kept the same community-based security model.

4. Question: Why is SNMPv2c not considered secure?
   Answer: The community string is effectively a shared clear-text credential.
   Explanation: Anyone with the string can access the same scope of data.

5. Question: What is the main architectural change in SNMPv3?
   Answer: SNMPv3 adds the User-based Security Model and separates access from plaintext community strings.
   Explanation: This enables authentication, privacy, and better access control.

6. Question: Which SNMP version is best for regulated telecom networks?
   Answer: SNMPv3.
   Explanation: It supports authentication and encryption, which are expected in hardened environments.

7. Question: How does SNMPv2c differ from SNMPv3 in deployment complexity?
   Answer: SNMPv2c is easier to configure but less secure, while SNMPv3 requires user and security profile management.
   Explanation: The additional configuration is the cost of improved protection.

8. Question: Why does SNMPv3 usually have higher overhead than SNMPv1 or v2c?
   Answer: Authentication and encryption require extra processing.
   Explanation: The security work adds latency and CPU cost.

## Security Model

9. Question: What is a community string in SNMP?
   Answer: A community string is a shared label used by SNMPv1 and SNMPv2c for access control.
   Explanation: It is simple, but it does not provide strong identity or confidentiality.

10. Question: What is USM in SNMPv3?
    Answer: USM is the User-based Security Model.
    Explanation: It provides user identity, authentication, and optional privacy.

11. Question: What does NO_AUTH_NO_PRIV mean?
    Answer: No authentication and no privacy.
    Explanation: It is the weakest SNMPv3 security level and should be avoided in production.

12. Question: What does AUTH_NO_PRIV mean?
    Answer: The session is authenticated but not encrypted.
    Explanation: It protects identity verification but still exposes payloads.

13. Question: What does AUTH_PRIV mean?
    Answer: The session is authenticated and encrypted.
    Explanation: It is the strongest standard SNMPv3 security level.

14. Question: Why is authentication important in network monitoring?
    Answer: It proves the manager is using a valid identity.
    Explanation: This reduces unauthorized access and spoofing.

15. Question: Why is privacy important in SNMP?
    Answer: Privacy protects the contents of management messages.
    Explanation: Without it, sensitive device state can be exposed to passive observers.

16. Question: What is the security risk of using a default community string?
    Answer: It makes unauthorized access trivial if the string is guessed or leaked.
    Explanation: Default strings are one of the most common SNMP weaknesses.

17. Question: What is the operational tradeoff between community-based and user-based security?
    Answer: Community-based security is simpler but weaker; user-based security is safer but more complex.
    Explanation: Production environments usually favor the stronger model.

18. Question: Which SNMPv3 security level should be used for sensitive telecom telemetry?
    Answer: AUTH_PRIV.
    Explanation: Sensitive telemetry deserves both identity verification and confidentiality.

19. Question: How should SNMP access be segmented in a secure NOC?
    Answer: By device class, privilege level, and security profile.
    Explanation: Limiting scope reduces blast radius if credentials are exposed.

20. Question: What is the role of encryption in the version migration strategy?
    Answer: It closes the gap left by community-based access control.
    Explanation: Migration to encryption reduces eavesdropping and payload disclosure.

## Authentication and Encryption

21. Question: What authentication algorithms are commonly associated with SNMPv3?
    Answer: MD5 and SHA.
    Explanation: SHA is generally preferred because it is stronger than MD5.

22. Question: Why is MD5 less preferred today?
    Answer: It is considered weaker and has known collision concerns.
    Explanation: Modern deployments usually prefer SHA-based authentication.

23. Question: What is the purpose of an authentication engine in this lab?
    Answer: It simulates credential validation and token generation.
    Explanation: The lab focuses on workflow visibility rather than protocol wire details.

24. Question: What privacy protocols are modeled here?
    Answer: DES and AES.
    Explanation: AES is the more modern choice for secure environments.

25. Question: Why is DES not recommended for production?
    Answer: It is outdated and offers weaker protection than AES.
    Explanation: Legacy support may exist, but it should not be a long-term target.

26. Question: What should be logged when a secure SNMPv3 request succeeds?
    Answer: The version, security level, and operational outcome.
    Explanation: Logs must be useful without leaking secrets or payloads.

27. Question: Why is deterministic encryption simulation useful in teaching labs?
    Answer: It makes outputs reproducible and easier to validate.
    Explanation: Students can compare the plaintext, encrypted payload, and decrypted result.

28. Question: How do authentication and privacy work together in SNMPv3?
    Answer: Authentication validates identity and privacy protects the payload.
    Explanation: They solve different problems and are typically paired in production.

29. Question: What does the lab demonstrate by comparing AES and DES outputs?
    Answer: The mechanics of protected payload handling.
    Explanation: The comparison is educational and reinforces security choices.

30. Question: Why should encryption be enabled for NOC access to telecom assets?
    Answer: Telecom status and device identifiers can expose operationally sensitive information.
    Explanation: Encryption reduces reconnaissance and interception risk.

## Telecom and NOC Operations

31. Question: Which telemetry values matter most in satellite and telecom monitoring?
    Answer: RF power, BER, carrier lock, frequency, symbol rate, and device health.
    Explanation: These values reveal both link quality and service stability.

32. Question: Why is BER important in a telecom monitoring dashboard?
    Answer: BER shows how many bits are arriving with errors.
    Explanation: Rising BER often indicates interference or link degradation.

33. Question: What does carrier lock tell an operator?
    Answer: Whether the modem or terminal is synchronized to the carrier.
    Explanation: Loss of lock usually means service disruption.

34. Question: Why do NOC teams care about response time in SNMP polling?
    Answer: It reflects polling overhead and device responsiveness.
    Explanation: A slower response can indicate load, security overhead, or device issues.

35. Question: Why use SNMPv2c for some read-only NOC polling?
    Answer: It is fast and widely supported on older devices.
    Explanation: Some environments accept the lower security only on isolated management networks.

36. Question: Why use SNMPv3 for firewall or core router monitoring?
    Answer: Those devices usually require stronger security and better auditability.
    Explanation: Their management data is more sensitive than basic edge telemetry.

37. Question: What makes a secure NOC dashboard useful to operators?
    Answer: It combines version, security, and operational status in one view.
    Explanation: Operators can quickly spot both technical and security posture issues.

38. Question: How should SatCom terminal state be interpreted?
    Answer: It indicates whether the terminal is operational, degraded, or offline.
    Explanation: This is a direct operational indicator used by support teams.

39. Question: Why is deterministic sample data important in training labs?
    Answer: It ensures every run produces the same reference outcome.
    Explanation: Stable outputs make it easier to learn and validate behavior.

40. Question: What is the benefit of a shared monitoring session model?
    Answer: It allows the same dashboard logic to represent different devices and versions.
    Explanation: Reusable session data reduces duplication and keeps the design clean.

## Migration and Troubleshooting

41. Question: What is the safest migration path from SNMPv1?
    Answer: Move from SNMPv1 to SNMPv2c only if necessary, then migrate to SNMPv3.
    Explanation: SNMPv3 is the end state for secure production use.

42. Question: What should be validated during migration to SNMPv3?
    Answer: User identity, authentication success, privacy setup, and device compatibility.
    Explanation: Migration fails if any of those pieces are misconfigured.

43. Question: What is a common cause of SNMPv3 authentication failure?
    Answer: Incorrect username, secret, or security level.
    Explanation: USM requires all of those elements to align.

44. Question: What is a common cause of encrypted SNMPv3 payload issues?
    Answer: Using the wrong privacy protocol or privacy secret.
    Explanation: Encryption and decryption must use the same configured parameters.

45. Question: How do you troubleshoot a missing SNMP response?
    Answer: Verify reachability, version, credentials, ACLs, and OID availability.
    Explanation: The failure may be network-related or authorization-related.

46. Question: Why should migration plans include operational rollback options?
    Answer: Device support gaps can appear during phased rollout.
    Explanation: A rollback path prevents monitoring outages.

47. Question: What is the biggest security win when moving to SNMPv3?
    Answer: Eliminating shared community-only access as the primary control.
    Explanation: Individual users and privacy lower the exposure of management traffic.

48. Question: What is the most important lesson from SNMP version selection?
    Answer: The version is a security and operations decision, not just a compatibility choice.
    Explanation: The wrong version can create avoidable risk in a production NOC.
