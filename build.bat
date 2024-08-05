@echo off
setlocal

REM Compile all Java files in drk/circuit directory
javac -cp "lib/*" -sourcepath ./ -d ./bin ./drk/circuit/*.java
if %ERRORLEVEL% neq 0 (
    echo Compilation failed in drk/circuit directory
    exit /b %ERRORLEVEL%
)

REM Compile the main game file
javac -cp "lib/*" -sourcepath ./ -d ./bin ./drk/game/KarnaughGame.java
if %ERRORLEVEL% neq 0 (
    echo Compilation failed for KarnaughGame.java
    exit /b %ERRORLEVEL%
)

REM Create the JAR file
jar cfm KarnaughGame.jar MANIFEST.MF -C bin . -C . .
if %ERRORLEVEL% neq 0 (
    echo JAR creation failed
    exit /b %ERRORLEVEL%
)

echo Build successful
endlocal
