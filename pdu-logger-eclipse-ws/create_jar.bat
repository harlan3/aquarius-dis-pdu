echo off

REM Prepare bin directory
if not exist "bin" mkdir bin
del /q bin\*

REM Clean and compile source
cd src
2>nul del "*.class" /s /f /q
javac -d ../bin orbisoftware/aquarius/pdu_logger/Main.java
cd ..

REM Create jar file
cd bin
echo Main-Class: orbisoftware.aquarius.pdu_logger.Main > tmp
jar cmf tmp ../pdu_logger.jar orbisoftware jargs
del tmp
cd ..

echo
echo pdu_logger.jar created
echo usage: java -jar pdu_logger.jar [OPTION]...
