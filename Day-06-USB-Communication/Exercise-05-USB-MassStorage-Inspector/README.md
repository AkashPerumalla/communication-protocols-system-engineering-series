# Exercise 05 — USB Mass Storage Inspector

Objective: Inspect connected mass storage devices and report vendor/product/capacity/filesystem.

Run:

```bash
javac --release 17 -d out $(find Day-06-USB-Communication/Exercise-05-USB-MassStorage-Inspector -name '*.java')
java -cp out Exercise05.USBStorageScanner
```

This is a simulated scanner that prints example devices.
