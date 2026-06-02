@echo off
setlocal
cd /d "%~dp0\..\Exercise-01-TCP-Reliable-Messaging"
javac TCPServer.java TCPClient.java
java TCPServer
