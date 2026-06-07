# Exercise 02 — USB Descriptor Analyzer

Objective: Parse and explain device/configuration/interface/endpoint descriptors.

Run:

```bash
javac --release 17 -d out $(find Day-06-USB-Communication/Exercise-02-USB-Descriptor-Analyzer -name '*.java')
java -cp out Exercise02.DescriptorAnalyzer
```

Expected: Human-readable breakdown of a sample device descriptor and raw hex.
