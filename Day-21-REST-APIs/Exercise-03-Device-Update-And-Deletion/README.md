# Exercise 03: Device Update And Deletion

Objective: Perform a controlled update and an administrator-only deletion while preserving integrity rules.

Architecture:
- Operator updates flow through `PUT /api/devices/{id}`.
- Admin deletes flow through `DELETE /api/devices/{id}`.
- Metric rows are removed before device deletion to satisfy foreign-key integrity.

Steps:
1. Update router firmware or status with `PUT /api/devices/1`.
2. Review the updated response payload.
3. Delete a test device as `admin`.
4. Verify the deleted ID in the response.

Expected Output:
- `DEVICE UPDATED`
- `DEVICE DELETED`

Solution:
- Create a temporary device first, then delete that device instead of removing seeded production-like inventory.

Learning Outcome:
- Understand secured mutation flows, integrity cleanup, and role separation.
