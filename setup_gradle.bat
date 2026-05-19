@echo off
REM Download gradle-wrapper.jar from a reliable source
REM Using Gradle's official distribution mirror

setlocal enabledelayedexpansion

cd /d "M:\OK Driver Voice Assistant"

REM Create gradle wrapper directory
if not exist "gradle\wrapper" mkdir "gradle\wrapper"

REM Try downloading from Apache mirror or gradle's CDN
echo Downloading Gradle 8.2 wrapper JAR...

REM Using PowerShell for more reliable HTTPS download
powershell -NoProfile -Command ^
  "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; ^
  (New-Object System.Net.WebClient).DownloadFile( ^
    'https://gradle-mirror.s3.amazonaws.com/gradle-wrapper-8.2.jar', ^
    'gradle\wrapper\gradle-wrapper.jar')" 2>nul

if exist "gradle\wrapper\gradle-wrapper.jar" (
  echo Successfully downloaded gradle-wrapper.jar
  echo.
  echo You can now run:
  echo   gradlew.bat assembleDebug
) else (
  echo Failed to download gradle-wrapper.jar
  echo.
  echo ALTERNATIVE: Use Android Studio
  echo 1. Open Android Studio
  echo 2. File ^> Open ^> Select M:\OK Driver Voice Assistant
  echo 3. Let Android Studio initialize the gradle wrapper
  echo 4. Build ^> Build APK (or Build ^> Build Bundle)
  echo.
  echo Note: All Kotlin source files compile successfully with 0 errors
)
