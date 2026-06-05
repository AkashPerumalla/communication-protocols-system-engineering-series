#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR=$(cd "$(dirname "$0")" && pwd)
cd "$ROOT_DIR"

# Compile all Java sources
echo "Compiling Java sources..."
javac */*.java

# Helper to start a server in the background and record PID
start_bg() {
  local cmd="$1"
  local log="$2"
  echo "Starting: $cmd"
  bash -c "$cmd" > "$log" 2>&1 &
  local pid=$!
  echo $pid
}

PIDS=()
LOGDIR=./test-logs
rm -rf "$LOGDIR" && mkdir -p "$LOGDIR"

# Utility: kill any process listening on a port (best-effort)
kill_port() {
  if command -v lsof >/dev/null 2>&1; then
    local pids=$(lsof -ti tcp:"$1" || true)
    if [ -n "$pids" ]; then
      echo "Killing processes on port $1: $pids"
      kill $pids || true
    fi
  fi
}

# Utility: wait for TCP port to become available
wait_for_port() {
  local port=$1
  local tries=0
  while ! (echo > /dev/tcp/localhost/$port) >/dev/null 2>&1; do
    sleep 0.05
    tries=$((tries+1))
    if [ $tries -gt 200 ]; then
      echo "Timeout waiting for port $port" >&2
      return 1
    fi
  done
  return 0
}

# Exercise 1
PID=$(start_bg "java -cp Exercise-01-RS232-Terminal-Basics:. RS232DeviceSimulator 7001" "$LOGDIR/ex1-server.log")
PIDS+=("$PID")
wait_for_port 7001
printf "STATUS\n" | nc localhost 7001 -w 2 > "$LOGDIR/ex1-client.out" || true
if grep -q "DEVICE READY" "$LOGDIR/ex1-client.out"; then
  echo "Exercise 1: PASS"
else
  echo "Exercise 1: FAIL"; cat "$LOGDIR/ex1-client.out"; exit 2
fi

# Exercise 2
PID=$(start_bg "java -cp Exercise-02-Modem-AT-Commands:. ATCommandSimulator 7002" "$LOGDIR/ex2-server.log")
PIDS+=("$PID")
wait_for_port 7002
printf "AT\nATI\nAT+CSQ\nAT+RST\n" | nc localhost 7002 -w 2 > "$LOGDIR/ex2-client.out" || true
if grep -q "OK" "$LOGDIR/ex2-client.out" && grep -q "MODEM v1.0" "$LOGDIR/ex2-client.out" && grep -q "SIGNAL=78" "$LOGDIR/ex2-client.out"; then
  echo "Exercise 2: PASS"
else
  echo "Exercise 2: FAIL"; sed -n '1,200p' "$LOGDIR/ex2-client.out"; exit 3
fi

# Exercise 3
PID=$(start_bg "java -cp Exercise-03-Telecom-Equipment-Configuration:. TelecomDeviceSimulator 7003" "$LOGDIR/ex3-server.log")
PIDS+=("$PID")
wait_for_port 7003
printf "SHOW STATUS\nSET FREQ 1450\nSET POWER HIGH\nSHOW STATUS\nSAVE\n" | nc localhost 7003 -w 2 > "$LOGDIR/ex3-client.out" || true
if grep -q "SAVED" "$LOGDIR/ex3-client.out" && grep -q "FREQ=1450 MHz" "$LOGDIR/ex3-client.out"; then
  echo "Exercise 3: PASS"
else
  echo "Exercise 3: FAIL"; sed -n '1,200p' "$LOGDIR/ex3-client.out"; exit 4
fi

# Exercise 4
PID=$(start_bg "java -cp Exercise-04-SatCom-Terminal-Monitoring:. SatComTerminalSimulator 7004" "$LOGDIR/ex4-server.log")
PIDS+=("$PID")
wait_for_port 7004
# Capture packets for up to 3 seconds (uses nc's -w idle timeout). Retry once if empty.
nc localhost 7004 -w 3 > "$LOGDIR/ex4-client.out" || true
if [ ! -s "$LOGDIR/ex4-client.out" ]; then
  sleep 0.2
  nc localhost 7004 -w 3 > "$LOGDIR/ex4-client.out" || true
fi
if grep -q "TERMINAL_ID" "$LOGDIR/ex4-client.out" && grep -q "PKT=" "$LOGDIR/ex4-client.out"; then
  echo "Exercise 4: PASS"
else
  echo "Exercise 4: FAIL"; sed -n '1,200p' "$LOGDIR/ex4-client.out"; exit 5
fi

# Exercise 5
PID=$(start_bg "java -cp Exercise-05-RS232-Diagnostics-Tool:. DiagnosticsServer 7005" "$LOGDIR/ex5-server.log")
PIDS+=("$PID")
wait_for_port 7005
java -cp Exercise-05-RS232-Diagnostics-Tool:. DiagnosticsClient localhost 7005 > "$LOGDIR/ex5-client.out" 2>&1 || true
if grep -q "Packets Sent" "$LOGDIR/ex5-client.out" && grep -q "Success Rate" "$LOGDIR/ex5-client.out"; then
  echo "Exercise 5: PASS"
else
  echo "Exercise 5: FAIL"; sed -n '1,200p' "$LOGDIR/ex5-client.out"; exit 6
fi

# Cleanup background servers
for pid in "${PIDS[@]}"; do
  kill "$pid" >/dev/null 2>&1 || true
done

echo "All tests passed. Logs are in $LOGDIR"
exit 0
