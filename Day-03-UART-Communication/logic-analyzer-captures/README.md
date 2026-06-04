# Logic Analyzer Captures

This folder contains logic analyzer waveforms and timing analysis from UART communication experiments.

Use a logic analyzer to visualize UART frames, verify baud rates, and diagnose timing issues.

## Recommended Tools

- **Sigrok/PulseView** (free, open-source)
- **Saleae Logic Analyzer** (paid)
- **USBee AX / USBee DX** (budget-friendly)

## How to Capture

1. Connect logic analyzer to TX and RX lines:
   - Channel 0: TX (transmitter output)
   - Channel 1: RX (receiver input)
2. Set sample rate: 100 kHz minimum for 9600 baud, 500 kHz+ for 115200 baud
3. Trigger on falling edge of start bit
4. Capture 100+ frames (depends on message frequency)
5. Export waveform as PNG or CSV
6. Save to this folder with descriptive name

## Expected Results

At 9600 baud, one bit period = ~104 microseconds. Start bit visible as transition from high (idle) to low.

## Reference

- [UART Frame Structure](../diagrams/uart-frame-structure.md)
- [Advanced Topics](../diagrams/uart-advanced-topics.md)
