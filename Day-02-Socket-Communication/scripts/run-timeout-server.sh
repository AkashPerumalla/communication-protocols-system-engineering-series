#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")/../Exercise-04-Socket-Timeout-Demo"
javac TimeoutServer.java TimeoutClient.java
java TimeoutServer
