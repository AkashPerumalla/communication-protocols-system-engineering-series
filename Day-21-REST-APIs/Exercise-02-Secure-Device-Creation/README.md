# Exercise 02: Secure Device Creation

Objective: Create a new IoT gateway through the write path and verify validation plus role-based security.

Architecture:
- Operator credentials are required.
- Bean Validation protects the request body.
- DeviceService enforces hostname and IP uniqueness.

Steps:
1. Authenticate as `operator`.
2. Submit `POST /api/devices` with a valid JSON body.
3. Confirm the response marker and success envelope.
4. Repeat with invalid data to observe structured failure handling.

Expected Output:
- `DEVICE CREATED`
- `VALIDATION PASSED`
- `REQUEST INVALID` for the bad request case

Solution:
- Use a unique hostname and IPv4 address such as `Gateway-02` and `10.21.5.82`.

Learning Outcome:
- Understand how enterprise APIs combine authentication, validation, and business-rule enforcement during creation.
