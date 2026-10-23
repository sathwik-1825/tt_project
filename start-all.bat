@echo off
title PharmaShield - Full-Stack Launcher
echo ============================================================
echo   PharmaShield - Medicine Inventory & Emergency Supply
echo ============================================================
echo.

set "PATH=C:\Users\lenovo\.tools\apache-maven-3.9.9\bin;C:\Users\lenovo\.tools\node-v20.18.0-win-x64;%PATH%"

echo [1/2] Starting Spring Boot Backend on http://localhost:8080 ...
start "PharmaShield Backend (Spring Boot)" cmd /k "cd backend && java -jar target\pharmacy-inventory-system-1.0.0.jar"

timeout /t 5 /nobreak >nul

echo [2/2] Starting React Frontend on http://localhost:3000 ...
start "PharmaShield Frontend (React)" cmd /k "cd frontend && npm run dev"

echo.
echo ============================================================
echo   PharmaShield is launching!
echo   Frontend: http://localhost:3000
echo   Backend:  http://localhost:8080
echo   H2 Console: http://localhost:8080/h2-console
echo.
echo   Demo Login Accounts (Password: password123):
echo   - Admin:      admin@pharmacy.com
echo   - Pharmacist: pharmacist@pharmacy.com
echo   - ICU Staff:  icu@hospital.com
echo   - ER Staff:   er@hospital.com
echo   - Supplier:   supplier@medisupply.com
echo ============================================================
timeout /t 3 >nul
start http://localhost:3000
exit
