@echo off
setlocal
cd /d "%~dp0\..\Exercise-02-UDP-Packet-Analyzer"
javac UDPServer.java UDPClient.java
java UDPServer
