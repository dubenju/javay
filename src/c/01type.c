#include <stdio.h>

int main() {
  printf("sizeof(char)  =%d\r\n", sizeof(char));
  printf("sizeof(short) =%d\r\n", sizeof(short));
  printf("sizeof(int)   =%d\r\n", sizeof(int));
  printf("sizeof(long)  =%d\r\n", sizeof(long));
  printf("sizeof(float) =%d\r\n", sizeof(float));
  printf("sizeof(double)=%d\r\n", sizeof(double));
  printf("sizeof(long double)=%d\r\n", sizeof(long double));

  printf("sizeof(char*)  =%d\r\n", sizeof(char *));
  printf("sizeof(short*) =%d\r\n", sizeof(short *));
  printf("sizeof(int*)   =%d\r\n", sizeof(int *));
  printf("sizeof(long*)  =%d\r\n", sizeof(long *));
  printf("sizeof(float*) =%d\r\n", sizeof(float *));
  printf("sizeof(double*)=%d\r\n", sizeof(double *));
  printf("sizeof(long double*)=%d\r\n", sizeof(long double *));

  return 0;
}
