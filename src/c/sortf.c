#include <stdio.h>
#include <stdlib.h>
/* #include <alloc.h> */

int rand_array(int ary[], int count) {
    int i;
    for (i = 0; i < count; i ++) {
        ary[i] = rand();
    }
    return 0;
}

int main(int argc, char * argv[]) {
    FILE * p;
    int  * pData;
    int count = 30000000;
    count = 300;
    int i;
    char buf[13];
    int pid = getpid();

    printf("pid = [%d]\n", pid);
printf("page size = %d\n", getpagesize());
/* printf("left size = %d\n", coreleft()); */

    pData = (int *)malloc(sizeof(int) * count);
    if (p == NULL) {
        printf("memory alloc error.\n");
        return -1;
    }
    memset(pData, 0, sizeof(int) * count);
    rand_array(pData, count);

    p = fopen("./sortin2.dat", "wb+");
    if (p == NULL) {
        printf("file open error.\n");
    }

    for (i = 0; i < count; i ++) {
        memset(buf, 0, sizeof(buf));
        sprintf(buf, "%10d\n", pData[i]);
        fwrite(buf, sizeof(char), strlen(buf), p);
    }
/* printf("left size = %d\n", coreleft()); */
    if (p != NULL) {
       fclose(p);
       p = NULL;
    }

    if (pData != NULL) {
        free(pData);
        pData = NULL;
    }
/* printf("left size = %d\n", coreleft()); */
    return 0;
}
