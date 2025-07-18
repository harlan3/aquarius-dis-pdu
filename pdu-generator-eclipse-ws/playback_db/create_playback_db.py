#!/usr/bin/python

import sys
import re
import os
import struct

# This script writes out the binary manifest and pdu packet contents for PDU
# Player.  Note that it writes out the data in binary big endian format 
# regardless of native platform type as required by the JVM.

class Global:
   prevPacketTime = -1 # Sentinel value
   currentFilePos = 0
   packetByteCount = 0
   packetTime = 0

class ParserState:
   NON_HEXPACKET_DATA = 0
   PROC_TIME_DATA = 1
   START_HEXPACKET_DATA = 2
   PROC_HEXPACKET_DATA = 3
   END_HEXPACKET_DATA = 4

def checkForTimeLine(line):

   if line[:18] == "Local packet time:":
      return True
   else:
      return False

def checkForHexLine(line):
   
   hexByteString = ""
   
   try:
      # The leftmost 4 characters are the byte count in a hex line
      hexByteString = line[:4]
   except IndexError:
      return False
   
   try:
      # Verify that all 4 characters are valid hex digits
      int(hexByteString[0:1], 16)
      int(hexByteString[1:2], 16)
      int(hexByteString[2:3], 16)
      int(hexByteString[3:4], 16)
   except ValueError:
      return False
   
   return True

def processTimeData(line):
   
   timeString = line.replace("Local packet time: ", "")
   Global.packetTime = int(timeString)

def startHexPacketData(outputManifestFile, outputBinFile, line):
   
   # Pull the hex bytes out of the hex output line
   hexByteLine = line[5:52]
   hexBytes = hexByteLine.split(' ')

   # Check for sentinel value.  Initialize prevPacketTime if set.
   if Global.prevPacketTime == -1:
      Global.prevPacketTime = Global.packetTime
   
   # Initialize packet byte count for packet
   Global.packetByteCount = 0
   
   # Write out packet timestamp in milliseconds into manifest (long long)
   data = struct.pack(">q", Global.packetTime)
   outputManifestFile.write(data)
   
   # Write out relative packet time in milliseconds into manifest (int)
   relativePacketTime = (Global.packetTime - Global.prevPacketTime);
   Global.prevPacketTime = Global.packetTime
   if relativePacketTime < 0:
      print("out of sequence packet found")
   data = struct.pack(">i", relativePacketTime)
   outputManifestFile.write(data)
   
   # Write out packet start position into manifest (int)
   data = struct.pack(">i", Global.currentFilePos)
   outputManifestFile.write(data)
   
   # Write out data into bin file (short)
   for i in hexBytes:
      data = struct.pack(">B", int(i, 16))
      outputBinFile.write(data)
      Global.currentFilePos+=1 # Update current file position
      Global.packetByteCount+=1 # Update packet byte count

def processHexPacketData(outputBinFile, line):

   # Pull the hex bytes out of the hex output line
   hexByteLine = line[5:52].rstrip()
   hexBytes = hexByteLine.split(' ')
   
   # Write out data into bin file (byte)
   for i in hexBytes:
      data = struct.pack(">B", int(i, 16))
      outputBinFile.write(data)
      Global.currentFilePos+=1 # Update current file position
      Global.packetByteCount+=1 # Update packet byte count

def endHexPacketData(outputManifestFile):
	
   # Write out packet size into manifest (short integer)
   packetSize = Global.packetByteCount
   data = struct.pack(">h", packetSize)
   outputManifestFile.write(data)	

def replace_extension(filename, new_extension):
	
   # Split the filename into the name and the current extension
   name, _ = os.path.splitext(filename)
   
   # Add the new extension to the name
   new_filename = name + new_extension
   return new_filename
    
def parseLogFile(inputFileName):
    
   parserState = ParserState.NON_HEXPACKET_DATA
   packetCount = 0
   currentFilePos = 0
   
   outputBinFileName = replace_extension(inputFileName, ".bin")
   outputManifestFileName = replace_extension(inputFileName, ".man")
   
   inputFile = open(inputFileName, "r", encoding="utf-16")
   outputManifestFile = open(outputManifestFileName,"wb")
   outputBinFile = open(outputBinFileName,"wb")
   
   # write out an integer as a placeholder for the final packet count
   data = struct.pack(">i", packetCount)
   outputManifestFile.write(data)
      
   # while log file data
   while True:
      
      line = inputFile.readline()
      
      if not line: break
      
      # check if this is a timeline
      isTimeLine = checkForTimeLine(line)
      
      # check if this is a hexline
      isHexLine = checkForHexLine(line)
      
      # Determine parser state
      if isTimeLine and parserState == ParserState.NON_HEXPACKET_DATA:
         parserState = ParserState.PROC_TIME_DATA
      elif isHexLine and parserState == ParserState.PROC_TIME_DATA:
         parserState = ParserState.START_HEXPACKET_DATA
      elif isHexLine and parserState == ParserState.START_HEXPACKET_DATA:
         parserState = ParserState.PROC_HEXPACKET_DATA
      elif not isHexLine and parserState == ParserState.PROC_HEXPACKET_DATA:
         parserState = ParserState.END_HEXPACKET_DATA
      elif not isHexLine:
         parserState = ParserState.NON_HEXPACKET_DATA
      
      # Action based on state machine
      if parserState == ParserState.PROC_TIME_DATA:
         processTimeData(line)
      elif parserState == ParserState.START_HEXPACKET_DATA:
         packetCount+=1
         startHexPacketData(outputManifestFile, outputBinFile, line)
      elif parserState == ParserState.PROC_HEXPACKET_DATA:
         processHexPacketData(outputBinFile, line)
      elif parserState == ParserState.END_HEXPACKET_DATA:
         endHexPacketData(outputManifestFile)

   # Reposition file pointer and write out packet count
   outputManifestFile.seek(0, 0)
   data = struct.pack(">i", packetCount)
   outputManifestFile.write(data)
   
   outputManifestFile.close()
   outputBinFile.close()
   inputFile.close()
   
def main():

   if len(sys.argv) != 2:
      print("Usage: python create_playback_db.py default_db.txt")
      sys.exit()
   
   parseLogFile(sys.argv[1])

main()

