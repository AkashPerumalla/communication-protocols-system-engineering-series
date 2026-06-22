# Exercise 08: Validation And Error Handling

Objective: Trigger controlled failures and inspect how the platform returns structured API errors.

Architecture:
- Bean Validation catches malformed payloads.
- GlobalExceptionHandler standardizes failure envelopes.
- Security still applies before controller execution.

Steps:
1. Submit a device payload with a blank hostname.
2. Submit a device payload with an invalid IPv4 address.
3. Request a device ID that does not exist.
4. Attempt a privileged operation with a low-privilege account.

Expected Output:
- `REQUEST INVALID`
- `RESOURCE NOT FOUND`
- `403 Forbidden` for unauthorized role usage

Solution:
- Compare field-level validation details with not-found and authorization failures.

Learning Outcome:
- Learn how strong error contracts improve API consumer behavior, testing, and operator troubleshooting.
