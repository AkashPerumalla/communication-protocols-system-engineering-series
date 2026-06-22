# Exercise 01: Device Inventory Overview

Objective: Explore the managed inventory endpoints used by NOC operators to review live device coverage.

Architecture:
- Viewer role authenticates through HTTP Basic.
- DeviceController delegates listing and retrieval to DeviceService.
- Repository paging returns the inventory slice.

Steps:
1. Call `GET /api/platform`.
2. Call `GET /api/devices` with viewer credentials.
3. Call `GET /api/devices/1` to inspect a specific router.
4. Review the `ApiResponse` wrapper and timestamp.

Expected Output:
- `API PLATFORM ACTIVE`
- `DEVICE RETRIEVED`

Solution:
- Use `viewer / viewer123` and verify the inventory count is seeded at startup.

Learning Outcome:
- Understand how resource collections and single-resource retrieval work in a secured REST API.
