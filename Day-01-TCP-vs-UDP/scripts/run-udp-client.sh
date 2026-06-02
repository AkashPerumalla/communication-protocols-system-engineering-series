#!/usr/bin/env sh
set -eu

cd "$(dirname "$0")/../Exercise-02-UDP-Packet-Analyzer"
java UDPClient
