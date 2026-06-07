```mermaid
graph TD
  App[Android App] -->|UsbManager| SystemService[UsbService]
  SystemService -->|Binder| KernelUSB[Linux Kernel USB Subsystem]
  KernelUSB --> Device[USB Device]
  SystemService -->|Broadcast| App
  App -->|Permission| UsbDeviceConnection
```
