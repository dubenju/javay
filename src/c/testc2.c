#include <stdio.h>
#include <stdlib.h>

typedef struct _REC2 {
  char a[16];  /* 16 */
}REC2;

int main() {
  REC2 rec2;
  FILE *p;
  p = fopen("C:\\prj\\c\\testdata.dat", "r+b");
  if (p == NULL) {
    printf("error.\n");
    return -1;
  }

  fread(&rec2, sizeof(REC2), 1, p);
  printf("a[0]=%d.\n", rec2.a[0]);
  printf("a[10]=%d.\n", rec2.a[10]);

  rec2.a[10] = 'A';
  rec2.a[11] = 'B';
  rec2.a[12] = 'C';

  fwrite(&rec2, sizeof(REC2), 1, p); 
  printf("a[10]=%d.\n", rec2.a[10]);
  if (ferror (p)) {
    printf ("Error Writing to file.\n");
  }

  fclose(p);
  p = NULL;
  printf("OK.\n");
  return 0;
}
