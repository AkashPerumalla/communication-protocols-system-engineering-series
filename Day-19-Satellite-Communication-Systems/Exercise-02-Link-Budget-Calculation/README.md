# Exercise 02 - Link Budget Calculation

## Objective
Compute realistic link budget outputs and interpret margin conditions for operational planning.

## Implementation Steps

1. Call GET /api/link-budget to inspect seeded budget baselines.
2. Submit POST /api/link-budget/calculate with a custom payload.
3. Compare EIRP, path loss, C/N, and link margin for your sample.
4. Repeat with reduced transmit power and observe margin collapse.

## Expected Output

- Marker LINK BUDGET CALCULATED is returned.
- Calculated fields include eirp, pathLoss, carrierPower, cnRatio, and linkMargin.
- Low-power inputs produce lower C/N and reduced margin.

## Solution Walkthrough

The service applies deterministic equations:

- EIRP from power, antenna gain, and implementation losses.
- Free-space path loss from frequency and slant range.
- Carrier power from budget chain combination.
- C/N from carrier, Boltzmann constant, noise temperature, and G/T.
- Margin from required C/N subtraction.

These are core engineering calculations used in satellite link dimensioning and acceptance testing.
