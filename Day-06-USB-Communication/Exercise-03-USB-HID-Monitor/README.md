# Exercise 03 — USB HID Monitor

Objective: Monitor HID events from devices like keyboard, mouse, and controllers.

Run:

```bash
javac --release 17 -d out $(find Day-06-USB-Communication/Exercise-03-USB-HID-Monitor -name '*.java')
java -cp out Exercise03.HIDDeviceSimulator
```

Then:

```bash
java -cp out Exercise03.HIDEventReader
```

Expected: The reader prints HID events such as KEY_A_DOWN, MOVE, LEFT_CLICK.
