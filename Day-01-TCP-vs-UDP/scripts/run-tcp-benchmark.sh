#!/usr/bin/env sh
set -eu

cd "$(dirname "$0")/../Exercise-03-Latency-Benchmark"
javac TCPBenchmarkClient.java UDPBenchmarkClient.java
java TCPBenchmarkClient
