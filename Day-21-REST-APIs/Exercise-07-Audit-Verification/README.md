# Exercise 07: Audit Verification

Objective: Verify that API calls are persisted as audit records for compliance and troubleshooting.

Architecture:
- ApiAuditInterceptor captures request timing and response status.
- AuditService writes immutable audit rows.
- AuditController exposes the recent audit trail to administrators.

Steps:
1. Execute several API calls as viewer and operator.
2. Authenticate as `admin`.
3. Call `GET /api/audit`.
4. Inspect endpoint, method, response code, and execution time values.

Expected Output:
- `AUDIT LOG GENERATED`

Solution:
- Run a create or update request before checking the audit endpoint so multiple HTTP methods appear in the log.

Learning Outcome:
- Understand how cross-cutting audit concerns can be implemented without putting logging code in every controller.
