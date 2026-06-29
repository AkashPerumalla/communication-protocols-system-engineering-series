# Exercise 07 - Deterministic Testing And Validation

## Objective

Prove deterministic behavior across API and MCP surfaces using pytest and marker assertions.

## Steps

1. Run all tests: `uv run pytest`
2. Re-run same tests and compare outputs.
3. Validate fixed timestamps and stable sorted records.

## Solution

Settings enforce deterministic mode; services return fixed metrics and ordered datasets.

## Outcome

Test runs are repeatable and suitable for enterprise CI pipelines.
