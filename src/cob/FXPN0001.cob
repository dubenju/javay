000000 IDENTIFICATION         DIVISION.                                 
000000 PROGRAM-ID.            DaFXPN0001.                               
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
000000*                                                                 
000000 DATA                   DIVISION.                                 
000000 FILE                   SECTION.                                  
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
000000     MOVE 12345           TO A02.                                 
000000     MOVE A02             TO A03.                                 
000000     DISPLAY '9(3)V9(2)=', A02.                                   
000000     DISPLAY '9(1)V9(4)=', A03.                                   
000000*                                                                 
000000     STOP RUN.                                                    
000000*                                                                 
