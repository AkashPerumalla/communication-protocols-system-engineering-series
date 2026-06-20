# Exercise 07 - Beam Footprint Calculation

## Objective
Apply a practical beam-footprint approximation to estimate coverage diameter from orbital and beamwidth assumptions.

## Implementation Steps

1. Choose a GEO satellite and beamwidth assumption (for example 1.2 degrees).
2. Use the practical approximation D approx H * theta where H is GEO altitude and theta is beamwidth in radians.
3. Compare narrow-beam and wide-beam results.
4. Relate estimated footprint size to VSAT site clustering and reuse planning.

## Expected Output

- Engineers can estimate coverage radius for planning discussions.
- Narrow beams produce smaller footprints and better reuse potential.
- Wide beams trade reuse for broader service area.

## Solution Walkthrough

Using GEO altitude around 35786 km:

- theta(1.2 deg) = 0.02094 rad
- footprint diameter approx 35786 * 0.02094 approx 749 km

This approximation helps in high-level capacity planning before refined orbital and antenna pattern tools are used.
