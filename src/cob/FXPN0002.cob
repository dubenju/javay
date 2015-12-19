000000 IDENTIFICATION         DIVISION.                                 
000000 PROGRAM-ID.            FXPN0002.                                 
000000 AUTHOR.                dubenju@126.com.                          
000000 DATE-WRITTEN.          2015.12.18.                               
000000 DATE-COMPILED.                                                   
000000*                                                                 
000000 ENVIRONMENT            DIVISION.                                 
000000 CONFIGURATION          SECTION.                                  
000000 SOURCE-COMPUTER.       HP.                                       
000000 OBJECT-COMPUTER.       HP.                                       
000000*                                                                 
000000 INPUT-OUTPUT           SECTION.                                  
000000 FILE-CONTROL.                                                    
000000     SELECT A-FILE ASSIGN TO "FXPN0002a.txt".                     
000000     SELECT B-FILE ASSIGN TO "FXPN0002b.txt".                     
000000*                                                                 
000000 DATA                   DIVISION.                                 
000000 FILE                   SECTION.                                  
000000 FD  A-FILE  RECORDING MODE IS F.                                 
000000 01 A-REC.                                                        
000000     05 ACC-NO          PIC 9(3)V9(2).                            
000000 FD  B-FILE  RECORDING MODE IS F.                                 
000000 01 B-REC.                                                        
000000     05 ACC-NO          PIC 9(1)V9(4).                            
000000*                                                                 
000000*                                                                 
000000 WORKING-STORAGE        SECTION.                                  
000000*                                                                 
000000 01  A02                 PIC 9(3)V9(2).                           
000000 01  A03                 PIC 9(1)V9(4).                           
000000**** USER-WORK-AREA.                                              
000000*                                                                 
000000 PROCEDURE              DIVISION.                                 
000000*                                                                 
000000 OPEN  OUTPUT A-FILE                                              
000000       OUTPUT B-FILE.                                             
000000*                                                                 
000000     MOVE 123.45           TO A02.                                
000000     MOVE 1.2345           TO A03.                                
000000     DISPLAY '9(3)V9(2)=', A02.                                   
000000     DISPLAY '9(1)V9(4)=', A03.                                   
000000     MOVE A02           TO ACC-NO OF A-REC.                       
000000     MOVE A03           TO ACC-NO OF B-REC.                       
000000     WRITE  A-REC.                                                
000000     WRITE  B-REC.                                                
000000*                                                                 
000000     CLOSE A-FILE                                                 
000000     B-FILE.                                                      
000000*                                                                 
000000     STOP RUN.                                                    
000000*                                                                 
