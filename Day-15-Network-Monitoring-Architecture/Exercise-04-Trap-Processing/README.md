# Exercise 04 - Trap Processing

## Objective
Understand asynchronous trap generation and event handling for operational incidents.

## Architecture
- `TrapGenerator`
- `TrapProcessor`
- `TrapEventRepository`
- Optional alert creation from trap severity

## Steps
1. Start the application.
2. Wait for trap generation or inspect the bootstrap event.
3. Call `GET /api/events`.
4. Review processed and unprocessed trap states.

## Expected Output
- Trap events persisted in H2
- Event records showing severity, message, and processing state

## Learning Outcome
You learn how event-driven monitoring complements periodic polling in a modern NOC.
