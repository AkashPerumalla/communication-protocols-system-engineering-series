```mermaid
graph LR
  PC[Host OS]
  Kernel --> USBStack[USB Stack]
  USBStack --> HubDriver[Hub Driver]
  USBStack --> ClassDrivers[Class Drivers (HID, CDC, MSC)]
  ClassDrivers --> App[User-space Applications]
  HubDriver -->|enumeration| Device[USB Device]
  Device -->|endpoints| Endpoints[Endpoint Descriptors]
```
