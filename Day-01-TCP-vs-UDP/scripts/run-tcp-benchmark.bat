@echo off
setlocal
cd /d "%~dp0\..\Exercise-03-Latency-Benchmark"
javac TCPBenchmarkClient.java UDPBenchmarkClient.java
java TCPBenchmarkClient
