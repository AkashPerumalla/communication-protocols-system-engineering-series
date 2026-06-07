## USB Interview Questions (50+)

1) Q: What is USB enumeration?
A: Process where host detects a device, reads descriptors, assigns an address, and loads a driver.
Explanation: Enumeration includes bus reset, Get Descriptor requests, Set Address, and configuration.

2) Q: What fields are in a USB device descriptor?
A: bLength, bDescriptorType, bcdUSB, bDeviceClass, bMaxPacketSize0, idVendor, idProduct, bNumConfigurations.
Explanation: Device descriptor provides device-level metadata used during enumeration.

3) Q: Difference between endpoint and interface?
A: Interface groups endpoints into a functional set; endpoints are communication channels (IN/OUT addresses).
Explanation: A device can expose multiple interfaces (e.g., CDC control + CDC data).

4) Q: What are the main USB transfer types?
A: Control, Bulk, Interrupt, Isochronous.
Explanation: Control for management, Bulk for large reliable transfers, Interrupt for low-latency polling, Isochronous for time-bound streaming.

5) Q: What is a SETUP packet?
A: The first packet of a control transfer carrying request type, request, value, index, length.
Explanation: The host uses SETUP to ask device descriptors or to set device state.

6) Q: What is bcdUSB?
A: Binary-coded decimal USB version (e.g., 0x0200 for USB 2.0).

7) Q: What is USB address assignment?
A: Host assigns a unique device address (1..127) via SetAddress during enumeration.

8) Q: What is endianness of fields in descriptors?
A: Little-endian for multi-byte fields (e.g., idVendor low byte first).

9) Q: What is class code 0x03?
A: HID (Human Interface Device).

10) Q: How does USB host detect device connection?
A: Electrical attach on D+/D- lines and device pull-up signaling speed.

11) Q: What is an endpoint descriptor?
A: Describes endpoint address, attributes (transfer type), max packet size, and interval.

12) Q: What is USB bulk transfer error handling?
A: Uses ACK/NAK/STALL; host can retry or reset endpoint on STALL.

13) Q: What is STALL used for?
A: Endpoint-level error state to indicate unsupported requests or fatal condition.

14) Q: Difference USB host vs OTG host?
A: OTG supports role negotiation (A/B) and can act as host or device depending on cable and negotiation.

15) Q: What is SuperSpeed's main difference?
A: Introduces streams, higher transfer rates, and new link management; uses SS-specific descriptors.

16) Q: How do HID reports work?
A: HID uses a report descriptor defining report format; host polls interrupt endpoint to receive reports.

17) Q: What is the USB CDC class?
A: Communications Device Class—supports virtual serial ports (ACM) over USB.

18) Q: How does a Mass Storage device present storage?
A: Uses Bulk-Only Transport with SCSI commands encapsulated over USB.

19) Q: What is an interface association descriptor (IAD)?
A: Groups multiple interfaces that belong to a single function (e.g., CDC control + data).

20) Q: What is control transfer stage sequence?
A: SETUP, optional DATA (IN or OUT), STATUS (handshake).

21) Q: How are USB speeds signaled?
A: Device pull-up resistor on D+ (full-speed) or D- (low-speed); high-speed uses negotiation via hub and hub reset.

22) Q: How to read descriptors via libusb on Linux?
A: Use libusb_get_device_descriptor() and libusb_get_config_descriptor().

23) Q: What is an interface class code 0x08?
A: Mass Storage Class.

24) Q: Why can't isochronous transfers be retried?
A: They prioritize timeliness over reliability—no retry or ACK mechanisms like bulk.

25) Q: What is USB endpoint 0?
A: The default control endpoint used for enumeration and standard requests.

26) Q: What is device qualifier descriptor?
A: Indicates capabilities for other speeds (used in USB 2.0 for high/low-speed compat).

27) Q: How are descriptors arranged hierarchically?
A: Device -> Configuration(s) -> Interface(s) -> Endpoint(s) (+ class-specific descriptors).

28) Q: How do drivers bind to devices on Linux?
A: Kernel matches VID/PID and class/subclass/protocol against driver tables, then binds driver.

29) Q: What is USB hub and port reset?
A: Hub resets the physical port to initialize device and determine speed, part of enumeration.

30) Q: What is the purpose of wMaxPacketSize0?
A: Maximum packet size for endpoint 0; affects control transfer behaviour and speed.

31) Q: How to capture USB traffic on Linux?
A: Use usbmon and Wireshark; usbmon provides kernel-level capture accessible by Wireshark.

32) Q: What is a USB descriptor type 0x21?
A: HID descriptor type.

33) Q: How do you fetch a string descriptor?
A: Use GET_DESCRIPTOR (STRING) with string index; strings are UTF-16LE.

34) Q: What is USB power negotiation (USB-PD)?
A: USB Power Delivery is a higher-layer protocol over CC lines for negotiating power contracts on USB-C.

35) Q: What is a Composite Device?
A: A device that implements multiple interfaces for different functions (e.g., keyboard + mass storage).

36) Q: How is a device re-enumerated?
A: By toggling device connection or performing a port reset via hub control.

37) Q: What is an Isochronous endpoint useful for?
A: Real-time audio/video streaming requiring steady bandwidth.

38) Q: What is USB descriptor string language ID?
A: First string descriptor (index 0) lists supported language IDs (e.g., 0x0409 for en-US).

39) Q: How does Android request permission to access a USB device?
A: App registers BroadcastReceiver and uses PendingIntent via UsbManager.requestPermission().

40) Q: What is the role of the host controller driver?
A: Manages the hardware controller (xHCI, EHCI, OHCI, UHCI) and schedules transfers.

41) Q: What is xHCI?
A: Extensible Host Controller Interface, the standard for USB 3.x host controllers.

42) Q: How to handle STALL in control transfers from firmware?
A: Fail request or implement clear feature; report proper status to host.

43) Q: What is the difference between device and host mode?
A: Device acts as peripheral; host controls bus, schedules transfers, and provides power.

44) Q: How to test USB descriptors without hardware?
A: Use simulators, libusb emulation, gadgetfs on Linux, or the TCP-based simulators in this lab.

45) Q: What is USB gadget on Linux?
A: Kernel subsystem to implement USB device functions in software (e.g., gadgetfs, FunctionFS).

46) Q: How to debug USB driver issues on Linux?
A: Use dmesg, syslog, usbmon, lsusb -v, and kernel debug logs.

47) Q: What is endpoint toggle sequence?
A: DATA0/DATA1 toggles for data integrity in bulk and interrupt transfers.

48) Q: What is the significance of bmRequestType in SETUP?
A: Specifies direction, type (standard/class/vendor), and recipient (device/interface/endpoint).

49) Q: How do vendor-specific requests work?
A: Host issues class/vendor SETUP requests that firmware handles per vendor-defined semantics.

50) Q: What is USB 3.0 SuperSpeed extra endpoint attribute?
A: Supports streams and higher packet sizes; endpoint companion descriptors exist.

51) Q: What is the Mass Storage BOT protocol?
A: Bulk-Only Transport encapsulating SCSI over USB using CBW/CSW structures.

52) Q: How to implement a virtual COM port on embedded device?
A: Implement CDC-ACM class descriptors, endpoints, and handle class-specific requests and ACM control.

53) Q: What is the role of the interface's bInterfaceProtocol?
A: Gives protocol info for class implementation (e.g., boot protocol for HID keyboard/mouse).

54) Q: How would you perform a firmware update over USB?
A: Use a vendor-specific protocol or DFU (Device Firmware Upgrade) class to transfer firmware and trigger flash.

55) Q: How to identify a device uniquely via USB?
A: Use VID/PID + serial number string descriptor.

56) Q: What is USB 2.0's split transaction?
A: Mechanism for hubs to communicate with low/full-speed devices behind high-speed hub using split transactions.

57) Q: What tools help analyze USB traffic on Windows?
A: USBPcap + Wireshark, Microsoft's Message Analyzer (legacy), USBlyzer.

58) Q: Why is endpoint 0 limited to small packets?
A: It uses wMaxPacketSize0 which is limited by device speed and affects descriptor stage sizes.

59) Q: How does the OS choose a driver if multiple match?
A: Matching order and driver priority; on Linux udev rules and module aliasing determine binding.

60) Q: What are best practices for firmware handling control requests?
A: Validate lengths, check bmRequestType, fail safely with STALL on unsupported, and minimize blocking in control handlers.
# Day-06 USB Interview Questions

This file contains interview questions and answers to prepare for USB-related interviews. (Truncated sample — full file in repo.)

Q: What is USB enumeration?
A: USB enumeration is the process where the host detects a new device, reads its device descriptor, assigns an address, and loads a driver.

Q: What are the primary USB transfer types?
A: Control, Bulk, Interrupt, Isochronous.

Q: What is a USB device descriptor?
A: The device descriptor contains vendor ID (VID), product ID (PID), USB version, device class, and other basic device-level information.

... (50+ Q&A to follow — exercise README files include specific Q&A sections.)
