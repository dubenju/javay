/* cp path\file to */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char * argv[]) {
  int result;

  FILE * fp_in;
  FILE * fp_out;

  unsigned long cnt_in;
  unsigned long cnt_out;

  long size_in;
  long size_out;

  char * buf_in;
  char * buf_out;

  long rec_len_in;
  long rec_len_out;

  int cnt_readed;
  int cnt_wroten;

  result = 0;
  fp_in = NULL;
  fp_out = NULL;
  buf_in = NULL;
  buf_out = NULL;

  fp_in = fopen(".\\input.txt", "rb");
  if (fp_in == NULL) {
    printf("in file open error.\r\n");
    result = -1;
  }

  if (result == 0) {
    fseek(fp_in, 0, SEEK_END);
    size_in = ftell(fp_in);
    printf("input file's size:%d.\r\n", size_in);
    fseek(fp_in, 0, SEEK_SET);
  }

  if (result == 0) {
    fp_out = fopen(".\\output.txt", "wb");
    if (fp_out == NULL) {
      printf("out file open error.\r\n");
      result = -1;
    }
  }

  rec_len_in = 512;
  rec_len_out = rec_len_in;

  if (result == 0) {
    buf_in = (char *) malloc(sizeof(char) * rec_len_in);
    if (buf_in == NULL) {
      printf("memory alloc error.\r\n");
      result = -1;
    }
  }

  if (result == 0) {
    buf_out = (char *) malloc(sizeof(char) * rec_len_out);
    if (buf_out == NULL) {
      printf("memory alloc error.\r\n");
      result = -1;
    }
  }

  cnt_in = 0;
  cnt_out = 0;

  while(result ==0 && !feof(fp_in)) {
    memset(buf_in, 0, sizeof(char) * rec_len_in);
    cnt_readed = fread(buf_in, sizeof(char), rec_len_in, fp_in);
    if (cnt_readed <= 0) {
      printf("end of read file by %d.\r\n", cnt_readed);
      break;
    }
    cnt_in ++;
    memset(buf_out, 0, sizeof(char) * rec_len_out);
    memcpy(buf_out, buf_in, cnt_readed);

    cnt_wroten = fwrite(buf_out, sizeof(char), cnt_readed, fp_out);
    if (cnt_wroten != cnt_readed) {
      printf("write out file error.\r\n");
      result = -1;
      break;
    }
    cnt_out ++;
  }

  if (result == 0) {
    size_out = ftell(fp_out);
    printf("output file's size:%d.\r\n", size_out);
  }

  if (buf_out != NULL) {
    free(buf_out);
    buf_out = NULL;
  }
  if (buf_in != NULL) {
    free(buf_in);
    buf_in = NULL;
  }
  if (fp_in != NULL) {
    fclose(fp_in);
    fp_in = NULL;
  }
  if (fp_out != NULL) {
    fclose(fp_out);
    fp_out = NULL;
  }

  printf("---- ----- ---------\r\n");
  printf("  in count:%09d.\r\n", cnt_in);
  printf(" out count:%09d.\r\n", cnt_out);

  return result;
}
