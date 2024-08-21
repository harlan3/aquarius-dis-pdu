#! python
# copy file and remove occurences of DISv7 string in file

import shutil

def copyfiles():
    shutil.copyfile("./pdu-logger-eclipse-ws/src/orbisoftware/aquarius/pdu_logger/PDU_TypeDISv7.java", "pdu-logger-eclipse-ws/src/orbisoftware/aquarius/pdu_logger/PDU_Type.java")
    shutil.copyfile("./pdu-logger-eclipse-ws/src/orbisoftware/aquarius/pdu_logger/ProcessDatagramThreadDISv7.java", "pdu-logger-eclipse-ws/src/orbisoftware/aquarius/pdu_logger/ProcessDatagramThread.java")

def replace_pdu_type():

    with open(r'./pdu-logger-eclipse-ws/src/orbisoftware/aquarius/pdu_logger/PDU_Type.java', 'r') as file: 
  
        # Reading the content of the file 
        # using the read() function and storing 
        # them in a new variable 
        data = file.read() 
    
        # Searching and replacing the text 
        # using the replace() function 
        data = data.replace("DISv7", "")

    # Opening our text file in write only 
    # mode to write the replaced content 
    with open(r'./pdu-logger-eclipse-ws/src/orbisoftware/aquarius/pdu_logger/PDU_Type.java', 'w') as file: 
  
        # Writing the replaced data in our 
        # text file 
        file.write(data) 

def replace_process_datagram_thread():

    with open(r'./pdu-logger-eclipse-ws/src/orbisoftware/aquarius/pdu_logger/ProcessDatagramThread.java', 'r') as file: 
  
        # Reading the content of the file 
        # using the read() function and storing 
        # them in a new variable 
        data = file.read() 
    
        # Searching and replacing the text 
        # using the replace() function 
        data = data.replace("DISv7", "")

    # Opening our text file in write only 
    # mode to write the replaced content 
    with open(r'./pdu-logger-eclipse-ws/src/orbisoftware/aquarius/pdu_logger/ProcessDatagramThread.java', 'w') as file: 
  
        # Writing the replaced data in our 
        # text file 
        file.write(data) 

if __name__=='__main__':
    copyfiles()
    replace_pdu_type()
    replace_process_datagram_thread()
    print("Configuration for DISv7 is complete.")