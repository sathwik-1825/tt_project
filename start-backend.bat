@echo off
title PharmaShield Backend
set "PATH=C:\Users\lenovo\.tools\apache-maven-3.9.9\bin;C:\Users\lenovo\.tools\node-v20.18.0-win-x64;%PATH%"
cd backend
java -jar target\pharmacy-inventory-system-1.0.0.jar
pause
