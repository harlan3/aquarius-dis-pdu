echo off

REM Prepare bin directory
if not exist "bin" mkdir bin
del /q bin\*

REM Clean and compile source
cd src
2>nul del "*.class" /s /f /q
javac -d ../bin orbisoftware/aquarius/pdu_sequencer/Desktop.java
cd ..

REM Create jar file
cd bin
echo Main-Class: orbisoftware.aquarius.pdu_sequencer.Desktop > tmp
jar cmf tmp ../pdu_sequencer.jar orbisoftware
del tmp
cd ..

echo
echo pdu_sequencer.jar created
echo usage: java -jar pdu_sequencer.jar
