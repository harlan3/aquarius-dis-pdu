echo off

REM Prepare bin directory
if not exist "bin" mkdir bin
del /q bin\*

REM Clean and compile source
cd src
2>nul del "*.class" /s /f /q
javac -d ../bin companion/MainApplication.java
cd ..

REM Create jar file
cd bin
echo Main-Class: companion.MainApplication > tmp
jar cmf tmp ../companion.jar companion
del tmp
cd ..

echo
echo companion.jar created
echo usage: java -jar companion.jar
