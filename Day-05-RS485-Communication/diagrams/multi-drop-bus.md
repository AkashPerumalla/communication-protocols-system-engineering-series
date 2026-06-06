```mermaid
graph LR
    Master[Master] --- Bus((RS485 Bus))
    Bus --- Device1[Device 01]
    Bus --- Device2[Device 02]
    Bus --- Device3[Device 03]
    note at Bus[style: dashed] Termination: 120Ω at both ends
```
