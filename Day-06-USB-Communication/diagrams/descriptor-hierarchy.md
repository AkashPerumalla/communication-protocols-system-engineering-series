```mermaid
graph TB
  DeviceDescriptor --> ConfigurationDescriptor
  ConfigurationDescriptor --> InterfaceDescriptor
  InterfaceDescriptor --> EndpointDescriptor
  InterfaceDescriptor --> HIDDescriptor
  EndpointDescriptor -->|IN/OUT| Host
```
