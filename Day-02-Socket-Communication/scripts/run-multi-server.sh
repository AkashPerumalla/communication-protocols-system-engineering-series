#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")/../Exercise-02-Multi-Client-Echo-Server"
javac MultiClientEchoServer.java ClientHandler.java EchoClient.java
java MultiClientEchoServer
