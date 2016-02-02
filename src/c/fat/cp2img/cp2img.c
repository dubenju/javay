/*
 * cp2img -f fat12 -i ifile.txt -o ofile.txt -chs 0-0-0
 * cp2img -f fat12 -i inputfile -o outputfile -chs 0,0,0
 * cp2img -f fat12 -i inputfile -o outputfile -path /path
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <memory.h>

typedef struct st_env {
  char fs; /* 1:fat12,2:fat16,3:fat32,4:ntfs */
  char * inflnm;
  char * outflnm;
  char option; /* 1:chs,2:path */
  char * ops;
} sv_env, *psv_env;

int chk_param(int argc, char *argv[], psv_env en) {
  char idx;
  char flg_fs;
  int fnlen;

  flg_fs = 0;

  if (argc < 9) {
    printf("argc=%d\r\n", argc);
    return -1;
  }

  idx = 0;
  if (memcmp(argv[idx], "cp2img", 6) != 0) {
    printf("program's name must be to cp2img\r\n");
    return -1;
  }

  idx ++;
  if(idx > argc) {
    printf("paramter error(%d)\r\n", idx);
    return -1;
  }
  if (memcmp(argv[idx], "-f", 2) != 0) {
    printf("file system option:-f\r\n");
    return -1;
  }
  flg_fs = 1;

  idx ++;
  if(idx > argc) {
    printf("paramter error(%d)\r\n", idx);
    return -1;
  }

  if (memcmp(argv[idx], "fat12", 5) == 0 || memcmp(argv[idx], "FAT12", 5) == 0) {
    en->fs = 1;
  } else if (memcmp(argv[idx], "fat16", 5) == 0 || memcmp(argv[idx], "FAT16", 5) == 0) {
    en->fs = 2;
  } else if (memcmp(argv[idx], "fat32", 5) == 0 || memcmp(argv[idx], "FAT32", 5) == 0) {
    en->fs = 3;
  } else {
    printf("file system :\r\n\tfat12\r\n\tfat16\r\n\tfat32\r\n\tntfs\r\n");
    return -1;
  }

  idx ++;
  if(idx > argc) {
    printf("paramter error(%d)\r\n", idx);
    return -1;
  }
  if (memcmp(argv[idx], "-i", 2) != 0) {
    printf("input file option:-i\r\n");
    return -1;
  }

  idx ++;
  if(idx > argc) {
    printf("paramter error(%d)\r\n", idx);
    return -1;
  }
  fnlen = strlen(argv[idx]);
  en->inflnm = (char *) malloc(sizeof(char)*(fnlen + 1));
  memset(en->inflnm, 0, (fnlen + 1));
  memcpy(en->inflnm, argv[idx], fnlen);

  idx ++;
  if(idx > argc) {
    printf("paramter error(%d)\r\n", idx);
    return -1;
  }
  if (memcmp(argv[idx], "-o", 2) != 0) {
    printf("output file option:-o\r\n");
    return -1;
  }

  idx ++;
  if(idx > argc) {
    printf("paramter error(%d)\r\n", idx);
    return -1;
  }
  fnlen = strlen(argv[idx]);
  en->outflnm = (char *) malloc(sizeof(char)*(fnlen + 1));
  memset(en->outflnm, 0, (fnlen + 1));
  memcpy(en->outflnm, argv[idx], fnlen);

  idx ++;
  if(idx > argc) {
    printf("paramter error(%d)\r\n", idx);
    return -1;
  }
  if (memcmp(argv[idx], "-chs", 2) == 0) {
    en->option = 1;
  } else if (memcmp(argv[idx], "-path", 2) == 0) {
    en->option = 2;
  } else {
    printf("output mode option:-chs\r\n");
    return -1;
  }

  idx ++;
  if(idx > argc) {
    printf("paramter error(%d)\r\n", idx);
    return -1;
  }
  fnlen = strlen(argv[idx]);
  en->ops = (char *) malloc(sizeof(char)*(fnlen + 1));
  memset(en->ops, 0, (fnlen + 1));
  memcpy(en->ops, argv[idx], fnlen);

  return 0;
}

int main(int argc, char *argv[]) {

  sv_env params;
  params.fs = -1;
  params.inflnm = NULL;
  params.outflnm = NULL;
  params.option = -1;
  params.ops = NULL;

  /* printf("argc=%d\r\n", argc); */
  if (chk_param(argc, argv, &params)) {
    printf("%s usage:\r\n", argv[0]);
    printf("%s -f fat12 -i inputfile -o outputfile -chs 0,0,0or\r\n", argv[0]);
    printf("%s -f fat12 -i inputfile -o outputfile -path /path or\r\n", argv[0]);
    printf("paramter check error\r\n.");
    return -1;
  }

printf("%d\r\n", params.fs);
printf("%s\r\n", params.inflnm);
printf("%s\r\n", params.outflnm);
printf("%d\r\n", params.option);
printf("%s\r\n", params.ops);

  if (params.fs == 1 || params.fs == 2 || params.fs == 3) {
    /* FAT12 FAT16 FAt32 */
    if (params.option == 1) {
      /* chs */
      get_chs();
      write_chs();
    }
    if (params.option == 2) {
      /* path */
      write_path();
    }
  }

int res;
FILE * fp = fopen(filename, "rwab");
if (fp == NULL) {
  printf("file open error.\r\n");
}

if (fp != NULL) {
  while ( ! feof(fp) {
    res = fread(buf, sizeof(char), len, fp);
    if (res <= 0) {
      printf("file read error.\r\n");
    }
    res = fwrite(buf, sizeof(char), len, fp);
    if (res != len) {
      printf("file write error.\r\n");
    }
  }
  res = fclose(fp);
  if (res == 0) {
    fp = NULL;
  } else {
    printf("file close error.\r\n");
  }
}





  if (params.inflnm != NULL) {
    free(params.inflnm);
    params.inflnm = NULL;
  }
  if (params.outflnm != NULL) {
    free(params.outflnm);
    params.outflnm = NULL;
  }
  if (params.ops != NULL) {
    free(params.ops);
    params.ops = NULL;
  }

  return 0;
}
