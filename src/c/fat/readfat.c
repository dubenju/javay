/* http://www.maverick-os.dk/ */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "readfat.h"

int show_usage(void);
int main_proc(const char *imgfile);
void parser_boots(const char * buffer);
void parser_rootdir(const char * buffer);
short toWord(const char *bytes);
long toDWord(const char *bytes);

int main(int argc, char *argv[]) {
  if (argc != 2) {
    return show_usage();
  }
  return main_proc(argv[1]);  
}

int show_usage(void) {
  return -1;
}

int main_proc(const char *imgfile) {
  FILE * pin;
  long file_size;
  char buf[513];
  int  read_cnt;
  unsigned long cnt_in;

  /* begin */
  cnt_in = 0;

  /* file open */
  pin = fopen(imgfile, "rb");
  if (pin == NULL) {
    return -1;
  }

  read_cnt = fseek(pin, 0, SEEK_END);
  if (read_cnt != 0) {
    printf("fseek end error\n");
    return -1;    
  }

  file_size = ftell(pin);
  printf("image file's size:%d\n", file_size);
  
  read_cnt = fseek(pin, 0, SEEK_SET);
  if (read_cnt != 0) {
    printf("fseek begin error\n");
    return -1;    
  }

  if (file_size == 0 || file_size % 512 != 0) {
    printf("512 error\n");
    return -1;
  }
  printf("/512:%d(=%d)\n", file_size / 512, 80*2*18);

  /* read & write file */
  while(!feof(pin)) {
    memset(buf, 0, sizeof(buf));
    read_cnt = fread(buf, sizeof(char), 512, pin);
    if (read_cnt != 512) {
      /* while read if */
      /* read do read while */
      break;
    }
    cnt_in ++;
    if (cnt_in == 1) {
      parser_boots(buf);
    }
    if (cnt_in == 20) {
      parser_rootdir(buf);
    }
  }

  if (pin != NULL) {
    fclose(pin);
    pin = NULL;
  }
  return 0;
}

void parser_boots(const char * buffer) {
  BOOTS bs;
  printf("sizeof(BOOTS)=%d\n", sizeof(BOOTS));
  printf("sizeof(BPB)=%d\n", sizeof(BPB));
  memset(&bs, 0, sizeof(BOOTS));
  memcpy(&bs, buffer + 0, 512);

  printf("          OEMName=[%8.8s]\n", bs.bs_OEMName);
  printf("   BytesPerSector=%d\n", toWord(bs.bpb.bpb_BytesPerSector));
  printf("SectorsPerCluster=%d\n", bs.bpb.bpb_SectorsPerCluster[0]);
  printf(" ReservedSectorst=%d\n", toWord(bs.bpb.bpb_ReservedSectorst));
  printf("        FatCopies=%d\n", bs.bpb.bpb_FatCopies[0]);
  printf("   RootDirEntries=%d\n", toWord(bs.bpb.bpb_RootDirEntries));
  printf("       NumSectors=%d\n", toWord(bs.bpb.bpb_NumSectors));
  printf("        MediaType=%d\n", bs.bpb.bpb_MediaType[0]);
  printf("    SectorsPerFAT=%d\n", toWord(bs.bpb.bpb_SectorsPerFAT));
  printf("    NumberOfHeads=%d\n", toWord(bs.bpb.bpb_NumberOfHeads));
  printf("    HiddenSectors=%d\n", toDWord(bs.bpb.bpb_HiddenSectors));
  printf("       SectorsBig=%d\n", toDWord(bs.bpb.bpb_SectorsBig));
  printf("           DrvNum=%d\n", bs.bs_DrvNum[0]);
  printf("       Reaserved1=%d\n", bs.bs_Reaserved1[0]);
  printf("          Bootsig=%d\n", bs.bs_Bootsig[0]);
  printf("           VolID=%d\n", toDWord(bs.bs_VolID));
  printf("           VolLab=[%11.11s]\n", bs.bs_VolLab);
  printf("      FileSysType=[%8.8s]\n", bs.bs_FileSysType);
  printf("      EndFlag=0X%X%X\n", bs.bs_EndFlag[0], bs.bs_EndFlag[1]);
}

void parser_rootdir(const char * buffer) {
  DIRENT dirEnt[16];
  printf("sizeof(DIRENT)=%d\n", sizeof(DIRENT));
  memset(dirEnt, 0, sizeof(dirEnt));
  memcpy(dirEnt, buffer, 512);
  char i = 0;
  for(i = 0; i < 16; i ++) {
    printf("[%8.8s][%3.3s]", dirEnt[i].fileName, dirEnt[i].fileExtension);
    printf("[%d][%03d]", dirEnt[i].fileAttrube[0], dirEnt[i].createTime[0]);
    if ((dirEnt[i].fileAttrube[0] & 0X20) == 0X20) {
      printf("A");
    } else {
      printf(" ");
    }
    if ((dirEnt[i].fileAttrube[0] & 0X04) == 0X04) {
      printf("S");
    } else {
      printf(" ");
    }
    if ((dirEnt[i].fileAttrube[0] & 0X02) == 0X02) {
      printf("H");
    } else {
      printf(" ");
    }
    if ((dirEnt[i].fileAttrube[0] & 0X01) == 0X01) {
      printf("R");
    } else {
      printf(" ");
    }
    if ((dirEnt[i].fileAttrube[0] & 0X10) == 0X10) {
      printf("D");
    } else {
      printf(" ");
    }
    printf("[%d][%d]\n", toWord(dirEnt[i].startClusters), toDWord(dirEnt[i].fileSize));
  }
}

short toWord(const char *bytes) {
  short result;
  result = 0;
/* printf("%d\n", bytes[0]);
printf("%d\n", bytes[1]); */
  /* result = (bytes[1] << 8) | bytes[0]; */
  result = 0X00FF & bytes[0];
  result = (bytes[1] << 8) | result;
  return result;
}

long toDWord(const char *bytes) {
  long result;
  result = 0;
  result = 0X000000FF & bytes[0];
  result = (bytes[1] << 8) | result;
  result = 0X0000FFFF & result;
  result = (bytes[2] << 16) | result;
  result = 0X00FFFFFF & result;
  result = (bytes[3] << 24) | result; 
  return result;
}
