#include <stdio.h>
#include <string.h>

int sum(int a, int b);
char * setdec(char *s, unsigned int ui);
int main(void) {
  char temp[11];
  char *p;
  printf("test %d.\n", sum(2, 3));
  memset(temp, 0, sizeof(temp));
  p = setdec(&temp[10], 4294967295);
  printf("ss%s\n", p);
  return 0;
}

int sum(int a, int b) {
  return a + b;
}

char * setdec(char *s, unsigned int ui) {
  do {
    *--s = (ui % 10) + '0';
  } while (ui /= 10);
  return s;
}

int sum1(int x, int y) {
  int z;
  z = x + y;
  return (z);
}
