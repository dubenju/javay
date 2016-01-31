#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char * pack2zone(char * in, char * out) {
    int i;
    int j;
    char src, hight, low;
    int len = strlen(in);
    j = 0;
    for(i = 0; i < len - 1; i ++) {
        src = *(in + i);
        hight = 0xF0 | (src >> 4);
        low = 0xF0 | (src);
        *(out + j) = hight;
        j ++;
        *(out + j) = low;
        j ++;
    }
    *(out + j) = *(in + i);
    return out;
}

int keycmp(char * a1, char * a2) {
    int result;
    result = memcmp(a1, a2, 2);
    return result;
}

int main(int argc, char * argv[]) {
    int i;
    char a[] = {
       0x35, 0xC8, 0x00
    };

    char * b = (char*)malloc(sizeof(char) * (2 * strlen(a)));
    memset(b, 0, strlen(a) + 1);
    pack2zone(a, b);
    printf("in:");
    for (i = 0; i < strlen(a); i ++) {
       printf("%#x", (char) *(a + i));
    }
    printf(".\nout:");
    for (i = 0; i < (2 * strlen(a) - 1); i ++) {
       printf("%#x", (char) *(b + i));
    }
    printf(".\n");
    return 0;
}
