#include <stdio.h>
#include <stdlib.h>

int main(int argc, char * argv[]) {
    int i;
    char test[] = "testtt";
    char * p = argv[1];
    char * p1 = (char *) malloc(30);
    memset(p1, 0, 30);
    sprintf(p1, "testt1234t");
    p = p1;
    while(*p != '\0') {
        printf("%c", *p);
        p ++;
    }
    return 0;
}
