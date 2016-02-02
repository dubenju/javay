/*****************************************************************/
/* editimg                                                       */
/*                                                               */
/*                                                               */
/*****************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "..\\readfat.h"

int show_usage(void);
int chk_option(const char *option);
int chk_fs(const char *fs);
int chk_size(const char *size);
int init_imgfile(const char * filename, const char * fs, const char * size);
int check_img_file(const char * filename);
int check_in_type(const char * in_type);
int check_out_type(const char * out_type);
int check_disk_pos(const char * disk_pos);
int check_dir_file(const char * dir_file);
int write_file_2_disk(const char * imgfile, const char * infile, const char * pos);
int write_file_2_file(const char * imgfile, const char * infile, const char * outfile);

short toWord(const unsigned char *bytes);

int main(int argc, char * argv[]) {

  int result;

  /* 
   * -1:error
   *  0:init
   *  1:read
   *  2:write
   */
  int ioption;

  /*
   * -1:erroe
   *  0:file
   *  1:dir
   */
  int iin_type;

  /*
   * -1:erroe
   *  0:disk
   *  1:dir
   */
  int iout_type;
  int len;
  char temp[9];

  result = 0;
  ioption = 0;
  iout_type = 0;

  if(argc < 3) {
    return show_usage();
  }

  /* check pgm's name */
  len = strlen(argv[0]);
  if (len > 8) {
    memset(temp, 0, sizeof(temp));
    memcpy(temp, argv[0] + len - 8, 8);
    printf("temp=%s\n", temp);
    if (strcmp(temp, "editimge") != 0) {
      /* editimge.exe error */
      printf("program'name must be editimge.\n");
      return -1;
    }
  } else if(strcmp(argv[0], "editimge") != 0) {
    printf("program'name must be editimge.\n");
    return -1;
  }

  /* check img file */

  /* check option */
  ioption = chk_option(argv[2]);
  if (ioption < 0) {
    printf("option must be one of [init,read,write]\n");
    return -1;
  } else if (ioption == 0) {

   /**********************************************/
   /*   init                                     */
   /**********************************************/

   /* check filesystem */
   if (chk_fs(argv[3]) < 0) {
     printf("filesystem must be fat12\n");
     return -1;
   }

   /* check size */
   if (chk_size(argv[4]) < 0) {
     printf("size must be 1.44MB\n");
     return -1;
   }
   result = init_imgfile(argv[1], argv[3], argv[4]);

 } else if (ioption == 1) {

    /*********************************************/
    /*    read                                   */
    /*********************************************/

  } else if (ioption == 2) {

    /*********************************************/
    /*    write                                  */
    /*********************************************/
    /* check img file init */
    if (check_img_file(argv[1]) < 0) {
      printf("img file uninited.\n");
      return -1;
    }

    /* check type */
    iin_type = check_in_type(argv[3]);
    if (iin_type < 0) {
      printf("in type undefined.\n");
      return -1;
    }

    if (iin_type == 0) {
      if (memcmp(argv[5], "to", 2) != 0) {
        printf("editimg imgfile write file aa to .\n");
        return -1;
      }
    }

    /* check type */
    iout_type = check_out_type(argv[6]);
    if (iout_type < 0) {
      printf("out type undefined.\n");
      return -1;
    }

    if (iout_type == 0) {

      /* disk */
      /* check disk position */
      if (check_disk_pos(argv[7]) <0) {
        printf("disk position error.\n");
        return -1;
      }
      result = write_file_2_disk(argv[1], argv[4], argv[7]);

    } else if (iout_type == 1) {

      /* dir */
      if ( check_dir_file(argv[7]) < 0 ) {
        printf("error at img file'dir&file.\n");
        return -1;
      }
      result = write_file_2_file(argv[1], argv[4], argv[7]);

    }
  }
  

  return result;
}

int show_usage(void) {
  printf("editimg <img file> init fat12 1474560.\n");
  printf("editimg <img file> write file <file> to disk H-C-S\n");
  printf("editimg <img file> write file <file> to dir <file>\n");
  return -1;
}

int chk_option(const char *option) {
  if (strcmp(option, "init") == 0) {
    return 0;
  } else if (strcmp(option, "read") == 0) {
    return 1;
  } else if (strcmp(option, "write") == 0) {
    return 2;
  } else {
    return -1;
  }
}

int chk_fs(const char *fs) {
  if (strcmp(fs, "fat12") == 0) {
    return 0;
  } else {
    return -1;
  }
}

int chk_size(const char *size) {
  if (strcmp(size, "1474560") == 0) {
    return 0;
  } else {
    return -1;
  }
}

int init_imgfile(const char * filename, const char * fs, const char * size) {
  FILE * file;
  unsigned long length;
  unsigned long index;
  char buf[513];

  index = 0;
  length = atol(size);
  file = fopen(filename, "wb");
  if (file == NULL) {
    return -1;
  }

  /* boot sector */
  memset(buf, 0, sizeof(buf));
  buf[00] = 0xEB; /* Win98 */
  buf[01] = 0x3C; /* Win98 */
  buf[02] = 0x90; /* Win98 */
  /* OME's name 8B */
  memcpy(buf + 3, "EDITIMG ", 8);
  /* Bytes Per Sector 2B */
  buf[11] = 0x00;
  buf[12] = 0x02;
  /* Sectors Per Cluster 1B */
  buf[13] = 0x01;
  /* Reserved Sectors 2B */
  buf[14] = 0x01;
  buf[15] = 0x00;
  /* Number of FATs 1B */
  buf[16] = 0x02;
  /* Root Entries 2B */
  buf[17] = 0xE0;
  buf[18] = 0x00;
  /* Small Sectors 2B*/
  buf[19] = 0x40;
  buf[20] = 0x0B;
  /* Media Descriptor 1B */
  buf[21] = 0xF0;
  /* Sectors Per FAT 2B*/
  buf[22] = 0x09;
  buf[23] = 0x00;
  /* Sectors Per Track 2B */
  buf[24] = 0x12;
  buf[25] = 0x00;
  /* Number of Heads 2B */
  buf[26] = 0x02;
  buf[27] = 0x00;
  /* Hidden Sectors 4B */
  buf[28] = 0x00;
  buf[29] = 0x00;
  buf[30] = 0x00;
  buf[31] = 0x00;
  /* Large Sectors 4B */
  buf[32] = 0x00;
  buf[33] = 0x00;
  buf[34] = 0x00;
  buf[35] = 0x00;

  buf[36] = 0x00;
  buf[37] = 0x00;
  buf[38] = 0x29;

  buf[39] = 0x2C;
  buf[40] = 0x0C;
  buf[41] = 0x04;
  buf[42] = 0x17;
  memcpy(buf + 43, "NO NAME    ", 11);
  memcpy(buf + 54, "FAT12   ", 8);

  buf[510] = 0x55; /* free Dos */
  buf[511] = 0xAA; /* free Dos */
  fwrite(buf, sizeof(char), 512, file);
  index ++;

  /* FAT1 */
  memset(buf, 0, sizeof(buf));
  buf[0] = 0xF0;
  buf[1] = 0xFF;
  buf[2] = 0xFF;
  fwrite(buf, sizeof(char), 512, file);
  index ++;

  for(; index < 10; index ++) {
    memset(buf, 0, sizeof(buf));
    fwrite(buf, sizeof(char), 512, file);
  }

  /* FAT2 */
  memset(buf, 0, sizeof(buf));
  buf[0] = 0xF0;
  buf[1] = 0xFF;
  buf[2] = 0xFF;
  fwrite(buf, sizeof(char), 512, file);
  index ++;

  for(; index < 19; index ++) {
    memset(buf, 0, sizeof(buf));
    fwrite(buf, sizeof(char), 512, file);
  }

  /* RootDirEntry */
  for(; index < 33; index ++) {
    memset(buf, 0, sizeof(buf));
    fwrite(buf, sizeof(char), 512, file);
  }

  /* file */
  for(; index < 2880; index ++) {
    memset(buf, 0xF6, sizeof(buf));
    fwrite(buf, sizeof(char), 512, file);
  }

  if(file != NULL) {
    fclose(file);
    file = NULL;
  }
printf("index=%lu\n", index);
  return 0;
}

/*
 * check image file's exist and size.
 */
int check_img_file(const char * filename) {
  FILE * file;
  long file_size;

  file = fopen(filename, "rb");
  if (file == NULL) {
    printf("check_img_file::fopen error(%s)\n", filename);
    return -1;
  }

  fseek(file, 0, SEEK_END);
  file_size = ftell(file);

  if (file !=NULL) {
    fclose(file);
    file = NULL;
  }

  if (file_size <= 0) {
    return -1;
  }
  return 0;
}

int check_in_type(const char * in_type) {
  if (strcmp(in_type, "file") == 0) {
    return 0;
  } else if (strcmp(in_type, "dir") == 0) {
    return 1;
  } else {
    return -1;
  }
}

int check_out_type(const char * out_type) {
  if (strcmp(out_type, "disk") == 0) {
    return 0;
  } else if (strcmp(out_type, "dir") == 0) {
    return 1;
  } else {
    return -1;
  }
}

int check_disk_pos(const char * disk_pos) {
  return 0;
}

/*
 * check file's name 8.3 rule
 */
int check_dir_file(const char * dir_file) {
  int result;
  int len;
  int idx;
  int idx_dot;

  printf("[check_dir_file]dir_file=%s\n", dir_file);
  result = 0;
  len = strlen(dir_file);
  idx_dot = len - 1;
  for(idx = len - 1; idx >= 0; idx --) {
    if (dir_file[idx] == '.') {
      idx_dot = idx;
      if ((len - idx - 1) > 3) {
        printf("not 8.[3] error.\n");
        result = -1;
        break;
        return -1;
      }
    }
    if (dir_file[idx] == '\\') {
      if ((idx_dot - idx - 1) > 8) {
        printf("not 8.[3] error.\n");
        result = -1;
      }
      break;
    }
  }

  return result;
}

int write_file_2_disk(const char * imgfile, const char * infile, const char * pos) {
  FILE *img;
  FILE *in;
  char buf[513];

  img = fopen(imgfile, "rb+");
  if (img == NULL) {
    return -1;
  }
  in = fopen(infile, "rb");
  if (in == NULL) {
    fclose(img);
    img = NULL;
    return -1;
  }
  while(!feof(in)) {
    if( fread(buf, sizeof(char), 512, in) != 512) {
      break;
    }
    fwrite(buf, sizeof(char), 512, img);
  }

  if (in != NULL) {
    fclose(in);
    in = NULL;
  }
  if (img != NULL) {
    fclose(img);
    img = NULL;
  }
printf("write OK\n");
  return 0;
}



int write_file_2_file(const char * imgfile, const char * infile, const char * outfile) {

  FILE *img;
  FILE *in;
  BOOTS bs;
  DIRENT direny;
  char flag;
  char buf[513];
  int idx;
  int cnt_dir;
  long filesize;
  long foffset;
  int sector_cnt;
  char temp1[2];
  char temp2[2];
  char buffer[4609];
  /* char fat_eny[6144]; */
  short fat_eny_cnt;
  short fat_eny[3172];
  int offset;

  printf("@write_file_2_file--\n");
  cnt_dir = 0;
  sector_cnt = 0;
  img = fopen(imgfile, "rb+");
  if (img == NULL) {
    printf("file(%s) open error.\n", imgfile);
    return -1;
  }

  /* read boot sector from image file */
  memset(&bs, 0, sizeof(BOOTS));
  fread(&bs, sizeof(BOOTS), 1, img);

  /* get image file's size */
  foffset = ftell(img);
  printf("position(512)=%ld.\n", foffset);

  in = fopen(infile, "rb");
  if (in == NULL) {
    fclose(img);
    img = NULL;
    printf("file(%s) open error.\n", infile);
    return -1;
  }
  fseek(in, 0, SEEK_END);
  filesize = ftell(in);
  if (filesize > 1457664) {
    fclose(img);
    img = NULL;
    fclose(in);
    in = NULL;
    printf("disk space error.(%ld)\n", filesize);
    return -1;
  }
  sector_cnt = filesize / 512;
  if ((filesize % 512) != 0) {
    sector_cnt ++;
  }
  printf("alloc %d\n", sector_cnt);
  /* FAT */
  cnt_dir = 0;
  fat_eny_cnt = 0;
  memset(fat_eny, 0, sizeof(fat_eny));

  /* read from image file */
  memset(buffer, 0, sizeof(buffer));
  fread(buffer, sizeof(char), 4608, img);

  /* get position from iamge file */
  foffset = ftell(img);
  printf("position(5120)=%ld.\n", foffset);

  for (idx = 0; idx < 4608; idx = idx + 3) {
    memset(temp1, 0, sizeof(temp1));
    memset(temp2, 0, sizeof(temp2));
    temp1[0] = buffer[idx + 1] & 0x0F;
    temp1[1] = buffer[idx];
    temp2[0] = buffer[idx + 1] & 0x0F0;
    temp2[1] = buffer[idx + 2];
    if (toWord((unsigned char *)temp1) == 0) {
      fat_eny[cnt_dir] = fat_eny_cnt;
      cnt_dir ++;
    }
    fat_eny_cnt ++;
    if (toWord((unsigned char *)temp2) == 0) {
      fat_eny[cnt_dir] = fat_eny_cnt;
      cnt_dir ++;
    }
    fat_eny_cnt ++;
  }
  for(idx = 0; idx < 10; idx ++) {
    printf("%d[%d]\n", idx, fat_eny[idx]);
  }

  /* DIREntry */
  /* fseek(img, 9728, SEEK_SET); */
  if( fseek(img, 4608, SEEK_CUR) != 0) {
    fclose(img);
    img = NULL;
    printf("file seek error.\n");    
    return -1;
  }

  foffset = ftell(img);
  printf("position(9728)=%ld.\n", foffset);

  printf("@write_file_2_file-before for\n");
  cnt_dir = 0;
  idx = 0;
  memset(buf, 0, sizeof(buf));
  while(outfile[cnt_dir] != '\0') {
  /*    printf("%c\n", outfile[cnt_dir]);*/

    if(outfile[cnt_dir] == '\\') {
      cnt_dir ++;
      continue;
    }

    if (outfile[cnt_dir] == '.') {
      while(idx < 8) {
        buf[idx] = ' ';
        idx ++;
      }
      cnt_dir ++;
      continue;
    }

    if ('a' <= outfile[cnt_dir] && outfile[cnt_dir] <= 'z') {
      buf[idx] = outfile[cnt_dir] - 32;
      idx ++;
    } else {
      buf[idx] = outfile[cnt_dir];
      idx ++;
    }
    if (idx > 10) {
      break;
    }
    cnt_dir ++;
  }
  printf("filename=[%s]\n", buf);

  idx = toWord(bs.bpb.bpb_RootDirEntries);
  printf("max=%d\n", idx);
  /* *** search in dir by name and get a flag fot update or create */
  flag = 0;
  cnt_dir = 0;
  /*while (cnt_dir < idx) {
    memset(&direny, 0, sizeof(DIRENT));
    fread(&direny, sizeof(char), 32, img);

    printf("filename=[%8.8s]\n", direny.fileName);
    if (memcmp(direny.fileName, buf, 8) == 0) {
      flag = 1;
      break;
    }
    if (direny.fileName[0] == 0) {
      flag = 0;
      break;
    }
    cnt_dir ++;
  }*/

  /* flag == 0, new create it.
     flag == 1, modify it. */
  printf("flag=%d,cnt=%d\n", flag, cnt_dir);
  if (flag == 0) {
    /* create new */
    memset(&direny, 0, sizeof(DIRENT));
    memcpy(direny.fileName,  buf, 8);
    memcpy(direny.fileExtension,  buf+8, 3);
    direny.fileAttrube[0] = 0x27;
    direny.Reserved[0] = 0x00;
    direny.createTime[0]= 0x00;
    direny.startClusters[0] = 0x02;
    direny.startClusters[1] = 0x00;

    printf("filesize=%ld.\n", filesize);
    direny.fileSize[0] = 0xFF & filesize;
    filesize = filesize >> 8;
    direny.fileSize[1] = 0xFF & filesize;
    filesize = filesize >> 8;
    direny.fileSize[2] = 0xFF & filesize;
    filesize = filesize >> 8;
    direny.fileSize[3] = 0xFF & filesize;

    /* for debug */
    filesize = ftell(img);
    printf("position=%ld.\n", filesize);
    /* for debug */

    fwrite(&direny, sizeof(DIRENT), 1, img);
  }
  if (flag == 1) {
    /* exist modify */
    memset(&direny, 0, sizeof(DIRENT));

    memcpy(direny.fileName,  buf, 8);        /* save */
    memcpy(direny.fileExtension,  buf+8, 3); /* save */
    direny.fileAttrube[0] = 0x27;            /* save */
    direny.Reserved[0] = 0x00;               /* save */
    direny.createTime[0]= 0x01;              /* save */
    direny.startClusters[0] = 0x02;          /* save */
    direny.startClusters[1] = 0x00;          /* save */

    printf("filesize=%ld.\n", filesize);
    direny.fileSize[0] = 0xFF & filesize;    /* filesize modify */
    filesize = filesize >> 8;
    direny.fileSize[1] = 0xFF & filesize;
    filesize = filesize >> 8;
    direny.fileSize[2] = 0xFF & filesize;
    filesize = filesize >> 8;
    direny.fileSize[3] = 0xFF & filesize;

    /* for debug */
    memcpy(direny.fileName,  "TESTTEST", 8);
    foffset = ftell(img);
    printf("position=%ld.\n", foffset);
    printf("out=%d.\n", direny.fileSize[0]);
    /* for debug */

    filesize = fwrite(&direny, sizeof(DIRENT), 1, img);
    if (filesize != 1) {
       printf("write file error(%ld).\n", filesize);
    } else {
      printf("dir entry rewrite OK.\n");
    }
  }

  /* *** write data *** */
  fseek(in, 0, SEEK_SET);
  fseek(img, 16896, SEEK_SET);
  cnt_dir = 2;
  while(!feof(in)) {
    memset(buf, 0, sizeof(buf));
    if( fread(buf, sizeof(char), 512, in) != 512) {
      if (cnt_dir == 2) {
        if (fat_eny[cnt_dir] != 2) {
          /* */
        } else {
        }
      } else {
        offset = fat_eny[cnt_dir] - fat_eny[cnt_dir - 1];
        if (offset > 1) {
          fseek(img, offset * 512, SEEK_CUR);
        }
      }
      printf("write_buf[%s]\n", buf);
      fwrite(buf, sizeof(char), 512, img);
      fat_eny[cnt_dir] = 0x0FFF;
      break;
    }

    if (cnt_dir == 2) {
      if (fat_eny[cnt_dir] != 2) {
        /* */
      }
    } else {
      offset = fat_eny[cnt_dir] - fat_eny[cnt_dir - 1];
      if (offset > 1) {
        fseek(img, offset * 512, SEEK_CUR);
      }
    }
    fwrite(buf, sizeof(char), 512, img);
    cnt_dir ++;
  }

  /* ReWrite FAT */

  if (in != NULL) {
    fclose(in);
    in = NULL;
  }

  /* write FAT1 & FAT2 */

  if (img != NULL) {
    fclose(img);
    img = NULL;
  }
  printf("write OK\n");
  return 0;
}

short toWord(const unsigned char *bytes) {
  short result;
  result = 0;
/* printf("[toWord0]%d\n", bytes[0]);
printf("[toWord1]%d\n", bytes[1]); */
  /* result = (bytes[1] << 8) | bytes[0]; */
  result = 0X00FF & bytes[0];
  result = (bytes[1] << 8) | result;
  return result;
}
