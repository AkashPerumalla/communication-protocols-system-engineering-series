#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")/../Exercise-03-Socket-Lifecycle-Monitor"
javac SocketStateServer.java SocketStateClient.java
java SocketStateServer
