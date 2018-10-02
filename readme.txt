Aquarius DIS PDU Suite - Orbis Software
---------------------------------------
The Aquarius DIS PDU Suite is a collection of simple tools supporting the
Distributed Interactive Simulation protocol used by the modeling and simulation
industry.  The suite provides the capability to easily heartbeart, record and
playback DIS PDUs.

License
-------
Aquarius DIS PDU Suite is licensed under the GNU GPL 3.0.

Source code is available from the Subversion repository at the project's
homepage:
    https://aquariusdispdu.svn.sourceforge.net

Setup
-----
Each of the tools within the suite can be compiled by executing the create_jar
script located in the directory of each tool.  The Java Developers Kit must be
installed prior to compilation.

The projects are preconfigured for eclipse.  To import into the eclipse 
workspace, select File->Import and choose to import an existing project.

PDU Logger
----------
PDU Logger parses and logs DIS PDUs in both human readable and hexadecimal
formats.  The hexadecimal output of PDU Logger can be used for populating the
packet.xml data of PDU Generator (used for a single PDU) as well as providing
the content for PDU Player.

PDU Logger works by associating a handler with each PDU type (see the src file
ProcessDatagramThread.java).  A default handler also exist to provide PDU 
identification and a hexadecimal dump of the PDU for unhandled PDU types. The
directory pdu_logger/pduHandler contains the DIS PDU definitions in XML format.

PDU Logger provides the capability to filter specific types of PDUs.  A default
log_filter.cfg file will be generated when this file is not found by PDU Logger.
All PDUs are logged by default, but this behavior can be modified by inserting
or removing the comment hash marks before each defined PDU type.

PDU Logger allows the specification of the port to listen on (3000 by default),
a DIS exercise ID filter, and the specification of a multicast group.  A
datagram socket is used by default if the multicast group is not specified. PDU
Logger supports both IPv4 and IPv6 addresses.

Use the -h option on this tool to view usage instructions.

PDU Generator
-------------
PDU Generator sends out a DIS PDU (or any datagram packet) at a regular 5 
second heartbeat interval.  The data sent in the PDU is defined within an xml 
data file.  Any hex data defined within child data elements of the 
datagramPacket parent element will appear sequentially in the DIS PDU packet.

PDU Player
----------
PDU Player provides the capability to playback the PDUs of an entire DIS
simulation exercise that was captured using PDU Logger.  These are the steps
to playback a previously captured exercise.

   1)  Use PDU logger to capture all simulation exercise PDUs
       cd pdu_logger
       java -jar pdu_logger.jar -p 3000 > default_db.txt

   2)  Generate the PDU manifest file and binary packet files.
       cd pdu_player/playback_db
       create_playback_db default_db.txt

       This will generate the default_db.man manifest, and default_db.bin 
       database files.

   3)  Start PDU Player and select the default_db.man file for playback.
       cd pdu_player
       java -jar pdu_player
       
       The previously logged DIS PDUs are now played.
       
Misc Tools
----------
UDP Reflector - Reflect the udp packets on a source port to a mirrored 
destination port. Data is captured at the data link layer using libpcap. This 
allows another process to bind to the port, and avoids the error "Bind: Address 
Already in Use". 

convert_tuple - Converts a DIS septuple to hex data that can be copied into the
entity type fields of the XML data file used by PDU Generator.


