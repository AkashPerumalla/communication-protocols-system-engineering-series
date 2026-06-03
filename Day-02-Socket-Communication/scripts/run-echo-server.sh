#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")/../Exercise-01-TCP-Echo-Server"
javac EchoServer.java EchoClient.java
java EchoServer
