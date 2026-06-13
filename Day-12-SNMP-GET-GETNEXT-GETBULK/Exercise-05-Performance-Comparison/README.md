# Exercise 05 - Performance Comparison

Compare GET, GETNEXT, and GETBULK using deterministic request, response, byte, and time summaries.

Run:
```bash
javac --release 17 -d out $(find ../core -name '*.java') PerformanceComparisonDemo.java
java -cp out PerformanceComparisonDemo
```
