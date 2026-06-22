# Exercise 04: Search And Filtering

Objective: Use search and categorical filters the way a NOC engineer would during incident triage.

Architecture:
- Search uses query parameters for optional conditions.
- Dedicated status, type, and location endpoints support fast drill-down.
- Repository queries handle filtering and paging.

Steps:
1. Call `GET /api/devices/search?q=Router`.
2. Call `GET /api/devices/status/OFFLINE`.
3. Call `GET /api/devices/type/MODEM`.
4. Call `GET /api/devices/location/Teleport-East`.

Expected Output:
- `DEVICE SEARCH COMPLETE`
- `DEVICE RETRIEVED`

Solution:
- Compare how keyword search differs from exact categorical filtering in the returned content.

Learning Outcome:
- Learn when to use search endpoints versus fixed-filter resource endpoints.
