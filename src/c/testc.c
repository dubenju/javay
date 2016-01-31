#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct _REC {
  char a;  /* 1 */
  char a1; /* 1 */
  short b; /* 2 */
  int c;   /* 4 */
  long d;  /* 4 */
  char temp[4];
}REC;

typedef struct _REC2 {
  char a[16];  /* 12 */
}REC2;


int main() {
  REC rec;
  REC2 rec2;
  FILE *p;

  rec.a = 20;
  rec.a1 = 21;
  rec.b=65535;
  rec.c=65535;
  rec.d=4294967296;
  rec.d=4294967295;
  rec.d=4294967294;
  rec.d=1;

  p=fopen("C:\\prj\\c\\testdata.dat", "wb");
  if (p == NULL) {
    printf("error.\n");
    return -1;
  }
 
  fwrite(&rec, sizeof(REC), 1, p);

  memset(&rec2, 0, sizeof(rec2));

  rec2.a[0] = 0xFF & rec.d;
  rec.d = rec.d >> 8;
  rec2.a[1] = 0xFF & rec.d;
  rec.d = rec.d >> 8;
  rec2.a[2] = 0xFF & rec.d;
  rec.d = rec.d >> 8;
  rec2.a[3] = 0xFF & rec.d;

  fwrite(&rec2, sizeof(REC2), 1, p);

  fclose(p);
  p = NULL;
  printf("OK.\n");

}
