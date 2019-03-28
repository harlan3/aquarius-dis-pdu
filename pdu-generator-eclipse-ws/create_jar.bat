echo off

REM Prepare bin directory
if not exist "bin" mkdir bin
del /q bin\*

REM Clean and compile source
cd src
2>nul del "*.class" /s /f /q
javac -d ../bin orbisoftware/aquarius/pdu_generator/Main.java
cd ..

REM Create jar file
cd bin
echo Main-Class: orbisoftware.aquarius.pdu_generator.Main > tmp
jar cmf tmp ../pdu_generator.jar orbisoftware
del tmp
cd ..

echo
echo pdu_generator.jar created
echo usage: java -jar pdu_generator.jar
