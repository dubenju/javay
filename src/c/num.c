#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct _bit {
    unsigned short data;
    struct _bit * next;
} bit, * pbit;

typedef struct _num {
    char signed_n; /* 1:+,-1:- */
    long len;
    pbit bits;
}num, * pnum;

/* ASCII */
int char2num(char * in, long len, pnum out) {
    return 0;
}

/*
 * S09C 
 * 0x07, 0x5B, 0x0CD, 0x15, 0x00
 * +++++++++++ ###########
 * hight-low, low-hight
*/
int bin2num(char * in, long len, pnum out) {
    long i;
    char buf[3];
    char * inbuf;
    unsigned short * p;
    short tmp;
    char temp = 1;

    printf("len:%ld.\n", len);
    memset(buf, 0, sizeof(buf));
    inbuf = (char *) malloc(sizeof(char) * (len + 1));
    if (inbuf == NULL) {
        printf("memory alloc error.\n");
    }
    memset(inbuf, 0, (len + 1));
    memcpy(inbuf, in, len);
    pbit it = out->bits;
    if ((0X80 & *(in)) == 0x80 ) {
        out->signed_n = -1;
        temp = 1;
        for (i = len - 1; i >= 0; i --) {
            tmp = 0x00FF & (~((char) *(inbuf + i)));
            tmp = tmp + temp;
            *(inbuf + i) = (char) 0x00FF & tmp;
/* printf("debug1:%02.2X.\n", *(inbuf + i)); */
            temp = (char) (0x00FF & (tmp >> 8));
/* printf("debug2:%02.2X.\n", temp); */
        }
    }
/* printf("tempbuf:");
debug_str(inbuf, len); */

    for(i = len - 1; i >= 0; i = i - 2) {
        memset(buf, 0, sizeof(buf));
        buf[0] = inbuf[i];
        if (i - 1 >= 0) {
            buf[1] = inbuf[i - 1];
        }
        buf[2] = 0;
        p = (unsigned short *) buf;
printf("%u.\n", *p);
        if (it == NULL) {
            it = (pbit) malloc(sizeof(bit));
            if (it == NULL) {
                printf("memory alloc error.\n");
            }
            it->next = NULL;
            it->data = *p;
            out->bits = it;
            out->len = 1;
        } else {
            while(it->next != NULL) {
                it = it->next;
            }
            it->next = (pbit) malloc(sizeof(bit));
            if (it == NULL) {
                printf("memory alloc error.\n");
            }
            it->next->next = NULL;
            it->next->data = *p;
            out->len ++;
        }
    }
    if (inbuf != NULL) {
        free(inbuf);
        inbuf = NULL;
    }
    return 0;
}

void debug_str(char * in, int len) {
    int i = 0;
    while(i < len) {
        printf("%2.2X", *(in + i));
        i ++;
    }
    printf(".\n");
}

/*
 * 0xF1, 0xF2, 0xF3, 0xF4, 0x0D5, 0x00
 */
int zone2num(char * in, long len, pnum out) {
    long i, j, short_len;
    char bufbcd[5];
    char buf[3];
    unsigned short * p;
    short tmp;
    char temp = 1;

    printf("len:%ld.\n", len);
    memset(bufbcd, 0, sizeof(bufbcd));
    memset(buf, 0, sizeof(buf));
    short_len = len / 4;
    if (len % 4 != 0) {
        short_len ++;
    }
    p = (unsigned short *) malloc(sizeof(short) * (short_len + 1));
    if (p == NULL) {
         printf("memory alloc error.\n");
    }
    memset(p, 0, sizeof(short) * (short_len + 1));

    if ((0XF0 & *(in + len - 1)) == 0xD0 ) {
        out->signed_n = -1;
    }
    printf("%d.\n", out->signed_n);

    /* F1F2F3F4D5 [1][2345](BCD Code) */
    for (i = len - 1, j = 0; i >= 0; i = i - 4, j ++ ) {
        memset(bufbcd, 0, sizeof(bufbcd));
        bufbcd[0] = 0x0F & in[i];
        if (i - 1 >= 0) {
            bufbcd[1] = 0x0F & in[i - 1];
        }
        if (i - 2 >= 0) {
            bufbcd[2] = 0x0F & in[i - 2];
        }
        if (i - 3 >= 0) {
            bufbcd[3] = 0x0F & in[i - 3];
        }
        bufbcd[4] = 0;
        printf("bufbcd%d:", j);
debug_str(bufbcd, 4);

        /* bcd to binary */
        p[j] = (short) (bufbcd[3] * 1000 + bufbcd[2] * 100 + bufbcd[1] * 10 + bufbcd[0]);
printf("%u.\n", p[j]); 
    }

    unsigned short * dst = (unsigned short *) malloc(sizeof(unsigned short) * (short_len + 1));
    if (dst == NULL) {
        printf("memory alloc error.\n");
    }
    memset(dst, 0, sizeof(unsigned short) * (short_len + 1));
    dst[short_len - 1] = p[short_len - 1];

    unsigned long sum = 0;
    unsigned long product = 0;
    unsigned long carry = 0;
    long index = 0;
    for (index = short_len - 2; index >= 0 ; index --) {
        for (i = short_len - 1; i >= 0 ; i--) {
            product = 10000 * dst[i] + carry;
            dst[i] = (unsigned short) product;
            carry = product >> 16;
        }

        sum = dst[short_len - 1] + p[index];
        dst[short_len -1] = (unsigned short) sum;
        carry = sum >> 16;

        for (i = short_len - 2; i >= 0; i--) {
            sum = dst[i] + carry;
            dst[i] = (unsigned short) sum;
            carry = sum >> 16;
        }
    }
/*
    for (index = 1; index < short_len; index ++) {
        product = 10000 * p[index] + p[index - 1] + carry;
        p[index - 1] = (unsigned short) product;
printf("p[%d]%d.\n", index - 1, p[index - 1]);
        carry = product >> 16;
printf("product:%ldcarry:%ld.\n", product, carry);
    }
    p[index - 1] = carry;
printf("p[%d]%d.\n", index - 1, p[index - 1]);
*/
    long new_len = short_len;
    for(j = short_len - 1; j >= 0; j --) {
        if (dst[j] == 0) {
            new_len --;
        } else {
            break;
        }
    }
    memset(p, 0, sizeof(short) * (short_len + 1));
    memcpy(p, dst, new_len);
printf("newlen%ld.\n", new_len);

    pbit it = out->bits;
    for (j = 0; j < new_len; j ++) {
        printf("dbg%d:%u.\n", j, p[j]);
        if (it == NULL) {
            it = (pbit) malloc(sizeof(bit));
            if (it == NULL) {
                printf("memory alloc error.\n");
            }
            it->next = NULL;
            it->data = *(p + j);
            out->bits = it;
            out->len = 1;
        } else {
            while(it->next != NULL) {
                it = it->next;
            }
            it->next = (pbit) malloc(sizeof(bit));
            if (it == NULL) {
                printf("memory alloc error.\n");
            }
            it->next->next = NULL;
            it->next->data = *(p + j);
            out->len ++;
        }
    }

    if (dst != NULL) {
        free(dst);
        dst = NULL;
    }
    return 0;
}

int pack2num(char * in, long len, pnum out) {
    return 0;
}

int num2char(pnum in, char * out) {
    
    return 0;
}

int num2bin(pnum in, char * out) {
    char * outbuf;
    short tmp;
    char temp = 1;
    long i = in->len;


    outbuf = (char *) malloc(sizeof(char) * (2 * in->len + 1));
    if (outbuf == NULL) {
        printf("memory alloc error.\n");
    }
    memset(outbuf, 0, (2 * in->len + 1));

    pbit it = in->bits;
    while(i > 0) {
printf("i=%d\n", i);
        *(outbuf + 2 * (i - 1) + 1) = (char) 0x00FF & it->data;
        *(outbuf + 2 * (i - 1)) = (char) 0x00FF & ((it->data) >> 8);
        it = it->next;
        i --;
    }

    if (in->signed_n == -1 ) {
        temp = 1;
        for (i = 2 * in->len - 1; i >= 0; i --) {
printf("i=%d.\n", i);
            tmp = 0x00FF & (~((char) *(outbuf + i)));
            tmp = tmp + temp;
            *(outbuf + i) = (char) 0x00FF & tmp;
printf("debug21:%02.2X.\n", *(outbuf + i));
            temp = (char) (0x00FF & (tmp >> 8));
printf("debug22:%02.2X.\n", temp);
        }
    }

    memcpy(out, outbuf, 2 * in->len);

    if (outbuf != NULL) {
        free(outbuf);
        outbuf = NULL;
    }

    return 0;
}

int num2zone(pnum in, char * out) {
    return 0;
}

int num2pack(pnum in, char * out) {
    return 0;
}

int add(pnum c, pnum a, pnum b) {
}

int sub(pnum c, pnum a, pnum b) {
}

int mul(pnum c, pnum a, pnum b) {
}

int div2(pnum c, pnum a, pnum b) {
}

int mod(pnum c, pnum a, pnum b) {
}

void debug_bit(pbit in) {
    if (in->next != NULL) {
        debug_bit(in->next);
    }
    printf("%#X", in->data);
}

void debug_num(pnum in) {
    long i;
    pbit everyone;
    printf("--------------------\n");
    printf("(len)(%ld).\n", in->len);
    if (in->signed_n == -1) {
        printf("-");
    } else {
        printf("+");
    }
    if (in->bits == NULL) {
        printf("NULL.\n");
        printf("--------------------\n");
        return ;
    }
    everyone = in->bits;
    i = 0;
    /* while(i < in->len) {
        printf("%04.4X", everyone->data);
        if (everyone->next == NULL) {
            break;
        }
        everyone = everyone->next;
        i ++;
    } */
    debug_bit(everyone);
    printf(".\n");
    printf("--------------------\n");
}

void free_bit(pbit in) {
    if (in->next != NULL) {
        free_bit(in->next);
        in->next = NULL;
    }
    in->data = 0;
    free(in);
    in = NULL;
}

void free_num(pnum in) {
    long i;
    pbit everyone;

    in->signed_n = 0;
    if (in->bits == NULL) {
        printf("NULL.\n");
        return ;
    }
    everyone = in->bits;
    free_bit(everyone);

    free(in);
    in = NULL;
}



int main(int argc, char * argv[]) {
    short test = 12345; /* 0x3039 */
    /* long testl = 123456789L; *//* 0x07 5B CD 15 */
    long testl = -123456789L; /* 0x07 5B CD 15 */
    num n;
    char bufc[] = { 0x15, 0x0CD, 0x5B, 0x07, 0x00 };
/*    char bufcob[] = { 0x07, 0x5B, 0x0CD, 0x15, 0x00 }; */
    char bufcob[] = { 0x0F8, 0x0A4, 0x32, 0x0EB, 0x00 };
    char bufzone[] = {0xF9, 0xF9, 0xF9, 0xF9, 0xF9, 0xF9, 0xF9, 0xF9, 0x0D9, 0x00};
    char bugpack[] = {0x12, 0x34, 0x0D5, 0x00};
    long * plcob = (long *) bufcob;
    long * plc = (long *) bufc;
    char * bufbin;

    n.signed_n = 1;
    n.len = 0L;
    n.bits = NULL;

    printf("long(c)=%ld,long(cob)=%ld.\n", *plc, *plcob);
    debug_num(&n);
    printf("***** test cob bin *****.\n");
    printf("inchar:");
    debug_str(bufcob, strlen(bufcob));
    bin2num(bufcob, strlen(bufcob), &n);
    debug_num(&n);

    bufbin = (char *) malloc(sizeof(char) * (2 * n.len + 1));
    if (bufbin == NULL) {
        printf("memory alloc error.\n");
    }
    memset(bufbin, 0, 2 * n.len + 1);
    num2bin(&n, bufbin);

    printf("tochar:");
    debug_str(bufbin, 2 * n.len);
    debug_num(&n);
    free_num(&n);
    n.signed_n = 1;
    n.len = 0L;
    n.bits = NULL;

    printf("***** test zone bin *****.\n");
    printf("inchar:");
    debug_str(bufzone, strlen(bufzone));
    zone2num(bufzone, strlen(bufzone), &n);
    debug_num(&n);

    free_num(&n);
    free(bufbin);
    bufbin = NULL;

    FILE * p = fopen("./num.dat", "wb+");
    fwrite(&test, sizeof(short), 1, p);
    fwrite(&testl, sizeof(long), 1, p);
    close(p);
    p = NULL;
    return 0;
}
