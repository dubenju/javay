#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
/* #include <windows.h> */
/* #include <mcheck.h> */

#define MAX_PARAM_CNT 3
#define MAX_PARAM_SUM_CNT 4
#define MAX_IN_FILE_CNT 50
#define MAX_SORT_KEY_CNT 500

typedef struct _sortkey {
     char sk_order; /* 1:ASC,-1:DESC */
     int  sk_type; /* 1:Zone, 2:Pack */
     int  sk_signed; /* 0:none,1:sort */
     unsigned int  sk_start;
     unsigned int  sk_len;
     unsigned int  sk_len2; /* when pack zone's len(2n-1) */
} sortkey, * psortkey;

typedef struct _obj {
     char * param[MAX_PARAM_CNT];
     int param_cnt;
     char * param_sum[MAX_PARAM_SUM_CNT];
     int param_sum_cnt;
     char * outfile_name;
     FILE * pout_file;
     char * infile_name[MAX_IN_FILE_CNT];
     FILE * pin_file[MAX_IN_FILE_CNT];
     long   infile_size[MAX_IN_FILE_CNT];
     int infile_cnt;
     int op_resaved_order; /* 1:resaved, 0:noresaved(default) */
     int op_charset;       /* 1:ascii */
     int op_sum;           /* 1:fist,2:last,3:min,4:max*/
     sortkey sort_keys[MAX_SORT_KEY_CNT];
     int sort_key_cnt;
     sortkey sum_items[MAX_SORT_KEY_CNT];
     int sum_item_cnt;
     unsigned int rec_len;
     unsigned int key_len;
} datamodel, * pdatamodel;

typedef struct _list {
    char * data;
    struct _list * next;
} list, * plist;

typedef struct _tnode {
    char * sortkeys;
    plist data;
    struct _tnode * left;
    struct _tnode * right;
} tnode, * ptnode;

int parse_setting(pdatamodel obj) {
    int i;
    char key_flag;
    char * p;
    char len[6];
    int  max_len = 4;
    int  len_cnt;

    memset(len, 0, sizeof(len));
    len_cnt = 0;

    for (i = 0; i < obj->param_cnt; i ++) {
        p = obj->param[i];
        key_flag = 0;
        while(*p != '\0') {
             /* printf("[%c]\n", *p); */
             if (*p == ' ') {
                 p ++;
                 continue;
             }
             if (*p == '-') {
                 if (*(p + 1) == 'L') { // record's Length
                     p +=2;
                     memset(len, 0, sizeof(len));
                     len_cnt = 0;
                     while((len_cnt <= max_len) && (*p != ' ' && *p != '\0')) {
                         len[len_cnt] = *p;
                         p ++;
                         len_cnt ++;
                     }
                     obj->rec_len = atoi(len);
                     if (*p == '\0') {
                         break;
                     }
                 }
                 if (*(p + 1) == 'k') { // keep
                     obj->op_resaved_order = 1;
                     p ++;
                 }
                 if (*(p + 1) == 'd') { // desc
                     key_flag = 1;
                     obj->sort_key_cnt ++;
                     obj->sort_keys[obj->sort_key_cnt - 1].sk_order = -1;
                     obj->sort_keys[obj->sort_key_cnt - 1].sk_signed = 0;
                     p ++;
                 }
                 if (*(p + 1) == 'z') { // zone
                     if (key_flag == 0) {
                         key_flag = 1;
                         obj->sort_key_cnt ++;
                         obj->sort_keys[obj->sort_key_cnt - 1].sk_order = 1;
                         obj->sort_keys[obj->sort_key_cnt - 1].sk_signed = 0;
                     }
                     obj->sort_keys[obj->sort_key_cnt - 1].sk_type = 1;
                     if (*(p + 2) == 's') { // by signed
                         obj->sort_keys[obj->sort_key_cnt - 1].sk_signed = 1;
                         p ++;
                     }
                     p ++;
                 }
                 if (*(p + 1) == 'p') { // pack
                     if (key_flag == 0) {
                         key_flag = 1;
                         obj->sort_key_cnt ++;
                         obj->sort_keys[obj->sort_key_cnt - 1].sk_order = 1;
                         obj->sort_keys[obj->sort_key_cnt - 1].sk_signed = 0;
                     }
                     obj->sort_keys[obj->sort_key_cnt - 1].sk_type = 2;
                     if (*(p + 2) == 's') { // by signed
                         obj->sort_keys[obj->sort_key_cnt - 1].sk_signed = 1;
                         p ++;
                     }
                     p ++;
                 }
                 if (*(p + 1) >= '0' && *(p + 1) <= '9') { // key's length
                     p ++;
                     /* printf("[debug][%s]\n", p); */
                     memset(len, 0, sizeof(len));
                     len_cnt = 0;
                     while((len_cnt <= max_len) && (*p != ' ' && *p != '\0' && *p != '-')) {
                         len[len_cnt] = *p;
                         p ++;
                         len_cnt ++;
                     }
                     obj->sort_keys[obj->sort_key_cnt - 1].sk_len = atoi(len);
                     key_flag = 0;
                     if (*p == '\0') {
                         break;
                     }
                 }
             }
             if (*p == '+') {
                 if (*(p + 1) >= '0' && *(p + 1) <= '9') {
                     p ++;
                     memset(len, 0, sizeof(len));
                     len_cnt = 0;
                     while((len_cnt <= max_len) && (*p != ' ' && *p != '\0' && *p != '-')) {
                         len[len_cnt] = *p;
                         p ++;
                         len_cnt ++;
                     }
                     obj->sort_keys[obj->sort_key_cnt - 1].sk_start = atoi(len);
                     if (*p == '\0') {
                         break;
                     }
                     p --;
                 }
             }
             p ++;
        }
    }

    for (i = 0; i < obj->param_sum_cnt; i ++) {
        p = obj->param_sum[i];
        key_flag = 0;
        while(*p != '\0') {
             /* printf("[%c]\n", *p); */
             if (*p == ' ') {
                 p ++;
                 continue;
             }
             if (*p == '-') {
                 /* if (*(p + 1) == 'L') { // record's Length
                     p +=2;
                     memset(len, 0, sizeof(len));
                     len_cnt = 0;
                     while((len_cnt <= max_len) && (*p != ' ' && *p != '\0')) {
                         len[len_cnt] = *p;
                         p ++;
                         len_cnt ++;
                     }
                     obj->rec_len = atoi(len);
                     if (*p == '\0') {
                         break;
                     }
                 }
                 if (*(p + 1) == 'k') { // keep
                     obj->op_resaved_order = 1;
                     p ++;
                 }
                 if (*(p + 1) == 'd') { // desc
                     key_flag = 1;
                     obj->sum_item_cnt ++;
                     obj->sum_items[obj->sum_item_cnt - 1].sk_order = -1;
                     p ++;
                 } */
                 if (*(p + 1) == 'z') { // zone
                     if (key_flag == 0) {
                         key_flag = 1;
                         obj->sum_item_cnt ++;
                         obj->sum_items[obj->sum_item_cnt - 1].sk_order = 1;
                     }
                     obj->sum_items[obj->sum_item_cnt - 1].sk_type = 1;
                     p ++;
                 }
                 if (*(p + 1) == 'p') { // pack
                     if (key_flag == 0) {
                         key_flag = 1;
                         obj->sum_item_cnt ++;
                         obj->sum_items[obj->sum_item_cnt - 1].sk_order = 1;
                     }
                     obj->sum_items[obj->sum_item_cnt - 1].sk_type = 2;
                     p ++;
                     /* printf("[debug][%s]\n", p); */
                 }
                 if (*(p + 1) >= '0' && *(p + 1) <= '9') { // key's length
                     p ++;
                     /* printf("[debug][%s]\n", p); */
                     memset(len, 0, sizeof(len));
                     len_cnt = 0;
                     while((len_cnt <= max_len) && (*p != ' ' && *p != '\0' && *p != '-')) {
                         len[len_cnt] = *p;
                         p ++;
                         len_cnt ++;
                     }
                     obj->sum_items[obj->sum_item_cnt - 1].sk_len = atoi(len);
                     key_flag = 0;
                     if (*p == '\0') {
                         break;
                     }
                 }
             }
             if (*p == '+') {
                 if (*(p + 1) >= '0' && *(p + 1) <= '9') {
                     p ++;
                     memset(len, 0, sizeof(len));
                     len_cnt = 0;
                     while((len_cnt <= max_len) && (*p != ' ' && *p != '\0' && *p != '-')) {
                         len[len_cnt] = *p;
                         p ++;
                         len_cnt ++;
                     }
                     obj->sum_items[obj->sum_item_cnt - 1].sk_start = atoi(len);
                     if (*p == '\0') {
                         break;
                     }
                     p --;
                 }
             }
             p ++;
        }
    }
    return 0;
}

int getsettings(pdatamodel obj) {
    char buf[21];
    int i;

    /* param */
    memset(buf, 0, sizeof(buf));
    sprintf(buf, "PARAM");
    obj->param_cnt = 0;

    char * p = getenv(buf);
    /* printf("%s=[%s]\n", buf, p); */
    if (p != NULL && strlen(p) > 0) {
        obj->param[obj->param_cnt] = (char *) malloc(sizeof(char) * (strlen(p) + 1));
        if (obj->param[obj->param_cnt] == NULL) {
            printf("memory alloc error.\n");
        }
        memset(obj->param[obj->param_cnt], '\0', sizeof(char) * (strlen(p) + 1));
        memcpy(obj->param[obj->param_cnt], p, strlen(p));
        obj->param_cnt ++;
    } else {
        for (i = 0; i < MAX_PARAM_CNT; i ++ ) {
            memset(buf, 0, sizeof(buf));
            sprintf(buf, "PARAM%d", (i + 1));
            p = getenv(buf);
            /* printf("[%s]\n", p); */
            if (p != NULL && strlen(p) > 0) {
                 obj->param[obj->param_cnt] = (char *) malloc(sizeof(char) * (strlen(p) + 1));
                 if (obj->param[obj->param_cnt] == NULL) {
                     printf("memory alloc error.\n");
                 }
                 memset(obj->param[obj->param_cnt], 0, sizeof(char) * (strlen(p) + 1));
                 memcpy(obj->param[obj->param_cnt], p, strlen(p));
                 obj->param_cnt ++;
            }
        }
    }

    /* param_sum */
    memset(buf, 0, sizeof(buf));
    sprintf(buf, "PARAMSUM");
    obj->param_sum_cnt = 0;

    p = getenv(buf);
    /* printf("%s=[%s]\n", buf, p); */
    if (p != NULL && strlen(p) > 0) {
        obj->param_sum[obj->param_sum_cnt] = (char *) malloc(sizeof(char) * (strlen(p) + 1));
        if (obj->param_sum[obj->param_sum_cnt] == NULL) {
            printf("memory alloc error.\n");
        }
        memset(obj->param_sum[obj->param_sum_cnt], 0, sizeof(char) * (strlen(p) + 1));
        memcpy(obj->param_sum[obj->param_sum_cnt], p, strlen(p));
        obj->param_sum_cnt ++;
    } else {
        for (i = 0; i < MAX_PARAM_SUM_CNT; i ++ ) {
            memset(buf, 0, sizeof(buf));
            sprintf(buf, "PARAMSUM%d", (i + 1));
            p = getenv(buf);
            /* printf("[%s]\n", p); */
            if (p != NULL && strlen(p) > 0) {
                 obj->param_sum[obj->param_sum_cnt] = (char *) malloc(sizeof(char) * (strlen(p) + 1));
                 if (obj->param_sum[obj->param_sum_cnt] == NULL) {
                     printf("memory alloc error.\n");
                 }
                 memset(obj->param_sum[obj->param_sum_cnt], 0, sizeof(char) * (strlen(p) + 1));
                 memcpy(obj->param_sum[obj->param_sum_cnt], p, strlen(p));
                 obj->param_sum_cnt ++;
            }
        }
    }

    /* sort in */
    memset(buf, 0, sizeof(buf));
    sprintf(buf, "SORTIN");
    obj->infile_cnt = 0;

    p = getenv(buf);
    /* printf("%s=[%s]\n", buf, p); */
    if (p != NULL && strlen(p) > 0) {
        obj->infile_name[obj->infile_cnt] = (char *) malloc(sizeof(char) * (strlen(p) + 1));
        if (obj->infile_name[obj->infile_cnt] == NULL) {
            printf("memory alloc error.\n");
        }
        memset(obj->infile_name[obj->infile_cnt], 0, sizeof(char) * (strlen(p) + 1));
        memcpy(obj->infile_name[obj->infile_cnt], p, strlen(p));
        obj->infile_cnt ++;
    } else {
        for (i = 0; i < MAX_IN_FILE_CNT; i ++ ) {
            memset(buf, 0, sizeof(buf));
            sprintf(buf, "SORTIN%d", (i + 1));
            p = getenv(buf);
            /* printf("[%s]\n", p); */
            if (p != NULL && strlen(p) > 0) {
                 obj->infile_name[obj->infile_cnt] = (char *) malloc(sizeof(char) * (strlen(p) + 1));
                 if (obj->infile_name[obj->infile_cnt] == NULL) {
                     printf("memory alloc error.\n");
                 }
                 memset(obj->infile_name[obj->infile_cnt], 0, sizeof(char) * (strlen(p) + 1));
                 memcpy(obj->infile_name[obj->infile_cnt], p, strlen(p));
                 obj->infile_cnt ++;
            }
        }
    }

    /* sort out */
    memset(buf, 0, sizeof(buf));
    sprintf(buf, "SORTOUT");

    p = getenv(buf);
    /* printf("%s=[%s]\n", buf, p); */
    if (p != NULL && strlen(p) > 0) {
        obj->outfile_name = (char *) malloc(sizeof(char) * (strlen(p) + 1));
        if (obj->outfile_name == NULL) {
            printf("memory alloc error.\n");
        }
        memset(obj->outfile_name, 0, sizeof(char) * (strlen(p) + 1));
        memcpy(obj->outfile_name, p, strlen(p));
    }

    return 0;
}

int chksettings(pdatamodel obj) {
    int i;
    int j;
    /* �P���ڃ`�F�b�N */
    /* (�P)���͕K�{�`�F�b�N */
    if (obj->param_cnt <= 0) {
        printf("PARAM or PARAM1 is need.\n");
        return -1;
    }

    if (obj->infile_cnt <= 0) {
        printf("SORTIN or SORTIN1 is need.\n");
        return -1;
    }

    if ((obj->outfile_name == NULL) || (strlen(obj->outfile_name) == 0)) {
        printf("SORTOUT is need.\n");
        return -1;
    }

    parse_setting(obj);

    if (obj->rec_len <= 0) {
        printf("-L record length is need.\n");
        return -1;
    }

    /* �d���`�F�b�N */
    obj->key_len = 0;
    for (i = 0; i <obj->sort_key_cnt; i ++) {
        if (obj->sort_keys[i].sk_start <= 0) {
            printf("%d sort key's start pos error.\n", (i + 1));
            return -1;
        }
        if (obj->sort_keys[i].sk_len <= 0) {
            printf("%d sort key's length error.\n", (i + 1));
            return -1;
        }
        if (obj->sort_keys[i].sk_start > obj->rec_len) {
            printf("%d sort key's start error.(%d > %d)\n", (i + 1), obj->sort_keys[i].sk_start, obj->rec_len);
            return -1;
        }
        if (obj->sort_keys[i].sk_len > obj->rec_len) {
            printf("%d sort key's length error.\n", (i + 1));
            return -1;
        }
        if (obj->sort_keys[i].sk_start + obj->sort_keys[i].sk_len - 1 > obj->rec_len) {
            printf("%d sort key's start error. (%d > %d)\n", (i + 1), obj->sort_keys[i].sk_start + obj->sort_keys[i].sk_len - 1, obj->rec_len);
            return -1;
        }
        if (obj->sort_keys[i].sk_type == 2) {
            // pack
            obj->sort_keys[i].sk_len2 = 2 * obj->sort_keys[i].sk_len - 1;
        } else {
            obj->sort_keys[i].sk_len2 = obj->sort_keys[i].sk_len;
        }
        obj->key_len = obj->key_len + obj->sort_keys[i].sk_len2;
    }
    for (i = 0; i <obj->sum_item_cnt; i ++) {
        if (obj->sum_items[i].sk_start <= 0) {
            printf("%d sort item's start pos error.\n", (i + 1));
            return -1;
        }
        if (obj->sum_items[i].sk_len <= 0) {
            printf("%d sort item's length error.\n", (i + 1));
            return -1;
        }
        if (obj->sum_items[i].sk_start > obj->rec_len) {
            printf("%d sort item's start error.\n", (i + 1));
            return -1;
        }
        if (obj->sum_items[i].sk_len > obj->rec_len) {
            printf("%d sort item's length error.\n", (i + 1));
            return -1;
        }
        if (obj->sum_items[i].sk_start + obj->sum_items[i].sk_len - 1 > obj->rec_len) {
            printf("%d sort item's start error.\n", (i + 1));
            return -1;
        }
    }

    /* �֘A�`�F�b�N */
    /* �\�[�g�L�[�A�W�v�E�W�񍀖ڂ̎w��`�F�b�N */
    /* (��)���̓t�@�C���T�C�Y�`�F�b�N */
    /* ���̓t�@�C���͑��݂��邩�ǂ����`�F�b�N */
    j = 0;
    for(i = 0; i < obj->infile_cnt; i ++) {
        if(obj->infile_name[i] != NULL) {
            obj->pin_file[i] = fopen(obj->infile_name[i], "rb");
            if (obj->pin_file[i] == NULL) {
                printf("input file open error.(%02d:%s)\n", i, obj->infile_name[i]);
                j = 1;
            }
            if (fseek(obj->pin_file[i],0, SEEK_END)) {
                printf("input file seek error.(%02d:%s)\n", i, obj->infile_name[i]);
                j = 1;
            }
            obj->infile_size[i] = ftell(obj->pin_file[i]);
            if (obj->infile_size[i] % obj->infile_size[i] != 0) {
                printf("input file's size error.(%02d:%s)\n", i, obj->infile_name[i]);
                j = 1;
            }
            if (fseek(obj->pin_file[i],0, SEEK_SET)) {
                printf("input file seek error. (%02d:%s)\n", i, obj->infile_name[i]);
                j = 1;
            }
            if (obj->infile_size[i] % obj->rec_len != 0) {
                printf("input file's size is error.(%d:%d:%d)\n", i, obj->infile_size[i], obj->rec_len);
                j = 1;
            }
        }
    }
    if (j == 1) {
        return -1;
    }

    /* �Ɩ��`�F�b�N */
    return 0;
}

void debug_settings(pdatamodel obj) {
    int i;
    printf("-----------------------------------------------------\n");
    printf("PARAM COUNT[%d]\n", obj->param_cnt);
    for(i = 0; i < obj->param_cnt; i ++) {
        printf("PARAM[%d]=%s\n", i + 1, obj->param[i]);
    }
    printf("PARAM SUM COUNT[%d]\n", obj->param_sum_cnt);
    for(i = 0; i < obj->param_sum_cnt; i ++) {
        printf("PARAM_SUM[%d]=%s\n", i + 1, obj->param_sum[i]);
    }

    printf("INPUT FILE COUNT[%d]\n", obj->infile_cnt);
    for(i = 0; i < obj->infile_cnt; i ++) {
        printf("SORTIN[%d]=%s\n", i + 1, obj->infile_name[i]);
    }

    printf("OUTPUT FILE [%s]\n", obj->outfile_name);
    printf("RECORD LENGTH[%d]\n", obj->rec_len);
    printf("SORTKEY LENGTH[%d]\n", obj->key_len);

    printf("sort keys' count:%d\n", obj->sort_key_cnt);
    for(i = 0; i < obj->sort_key_cnt; i ++) {
        printf("SORT KEY%dTYPEt[%d]s[%d]o[%d]b[%d]l[%d]\n", i + 1, 
               obj->sort_keys[i].sk_type,
               obj->sort_keys[i].sk_signed,
               obj->sort_keys[i].sk_order,
               obj->sort_keys[i].sk_start,
               obj->sort_keys[i].sk_len);
    }

    printf("sum items' count:%d\n", obj->sum_item_cnt);
    for(i = 0; i < obj->sum_item_cnt; i ++) {
        printf("SUM ITEM%dTYPE[%d][%d][%d][%d]\n", i + 1, 
               obj->sum_items[i].sk_type,
               obj->sum_items[i].sk_order,
               obj->sum_items[i].sk_start,
               obj->sum_items[i].sk_len);
    }
    for (i = 0; i < obj->infile_cnt; i ++) {
        printf("input file[%d][%s][%d].\n", i + 1, obj->infile_name[i], obj->infile_size[i]);
    }
    printf("-----------------------------------------------------\n");
}


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

void getsortkey(char * sortkey_buf, char * read_buf, pdatamodel obj) {
    int i;
    char * tmp, * tmp1;
    int pos = 0;
    tmp = NULL;
    tmp1 = NULL;
    for (i = 0; i < obj->sort_key_cnt; i ++) {
        if (obj->sort_keys[i].sk_type == 2) {
            // pack
            tmp1 = (char *) malloc(sizeof(char) * (obj->sort_keys[i].sk_len + 1));
            if (tmp1 == NULL) {
                printf("memory alloc error.\n");
            }
            memset(tmp1, 0, obj->sort_keys[i].sk_len + 1);
            memcpy(tmp1, read_buf + obj->sort_keys[i].sk_start - 1, obj->sort_keys[i].sk_len);

            tmp = (char *) malloc(sizeof(char) * (obj->sort_keys[i].sk_len2 + 1));
            if (tmp == NULL) {
                printf("memory alloc error.\n");
            }
            memset(tmp, 0, obj->sort_keys[i].sk_len2 + 1);
            pack2zone(tmp1, tmp);

            memcpy(sortkey_buf + pos, tmp, obj->sort_keys[i].sk_len2);
            if (tmp1 != NULL) {
                free(tmp1);
                tmp1 = NULL;
            }
            if (tmp != NULL) {
                free(tmp);
                tmp = NULL;
            }
        } else {
            memcpy(sortkey_buf + pos, read_buf + obj->sort_keys[i].sk_start - 1, obj->sort_keys[i].sk_len);
        }
        pos = pos + obj->sort_keys[i].sk_len2;
    }
    if (tmp1 != NULL) {
        free(tmp1);
        tmp1 = NULL;
    }
    if (tmp != NULL) {
        free(tmp);
        tmp = NULL;
    }
/* printf("getsortkey@[%s].\n", sortkey_buf); */
}

int keycmp(char * a1, char * b2, pdatamodel obj) {
    int i;
    int start = 0;
    char c;
    int sign = 1;
    int result = 0;
    for(i = 0; i < obj->sort_key_cnt; i ++) {
        if (obj->sort_keys[i].sk_signed == 1) {
            c = (char) *(a1 + start + obj->sort_keys[i].sk_len - 1);
            if ((0x0F & (c >> 4)) == 0x0D) {
                 sign = -1;
            }
            c = (char) *(b2 + start + obj->sort_keys[i].sk_len - 1);
            if ((0x0F & (c >> 4)) == 0x0D) {
                 sign = -1;
            }
        }
        if ((sign * obj->sort_keys[i].sk_order * memcmp(a1 + start, b2 + start, obj->sort_keys[i].sk_len)) == 0) {
            start = start + obj->sort_keys[i].sk_len;
            continue;
        }
        if ((sign * obj->sort_keys[i].sk_order * memcmp(a1 + start, b2 + start, obj->sort_keys[i].sk_len)) < 0) {
            result = -1;
            break;
        }
        if ((sign * obj->sort_keys[i].sk_order * memcmp(a1 + start, b2 + start, obj->sort_keys[i].sk_len)) > 0) {
            result = 1;
            break;
        }
        printf("keycmp:%s vs %s.\n", a1, b2);
    }
/* printf("[DEBUG]keycmp:%d(%s,%s).\n", result, a1, b2); */
    return result;
}


void addtnode(ptnode p, char * sortkey, char * data, pdatamodel obj) {
    plist pe = NULL;
    plist pre = NULL;
    if (p->sortkeys == NULL) {
        /* root */
        p->sortkeys = (char *) malloc(sizeof(char) * (strlen(sortkey) + 1));
        if (p->sortkeys == NULL) {
            printf("memory alloc error.\n");
        }
        memset(p->sortkeys, 0, sizeof(char) * (strlen(sortkey) + 1));
        memcpy(p->sortkeys, sortkey, strlen(sortkey));

        p->data = (plist) malloc(sizeof(list));
        if (p->data == NULL) {
            printf("memory alloc error.\n");
        }
        p->data->data = (char *) malloc(sizeof(char) * (strlen(data) + 1));
        if (p->data->data == NULL) {
            printf("memory alloc error.\n");
        }
        memset(p->data->data, 0, sizeof(char) * (strlen(data) + 1));
        memcpy(p->data->data, data, strlen(data));

       p->data->next = NULL;
    } else if (memcmp(sortkey, p->sortkeys, strlen(sortkey)) == 0) {
        /* current */
        pe = p->data;
        while(pe != NULL) {
            pre = pe;
            pe = pe->next;
        }
        if (pe == NULL) {

            pe = (plist) malloc(sizeof(list));
            if (pe == NULL) {
                printf("memory alloc error.\n");
            }
            pe->data = (char *) malloc(sizeof(char) * (strlen(data) + 1));
            if (pe->data == NULL) {
                printf("memory alloc error.\n");
            }
            memset(pe->data, 0, sizeof(char) * (strlen(data) + 1));
            memcpy(pe->data, data, strlen(data));

           pe->next = NULL;

           if (pre != NULL) {
               pre->next = pe;
           }
        }
    /*} else if (memcmp(sortkey, p->sortkeys, strlen(sortkey)) < 0) { */
    } else if (keycmp(sortkey, p->sortkeys, obj) < 0) {
        /* left */
        if (p->left == NULL) {
            p->left = (ptnode)malloc(sizeof(tnode));
            if (p->left == NULL) {
                printf("memory alloc error.\n");
            }
            p->left->sortkeys = NULL;
            p->left->data = NULL;
            p->left->left = NULL;
            p->left->right = NULL;
        }
        addtnode(p->left, sortkey, data, obj);
    } else {
        /* right */
        if (p->right == NULL) {
            p->right = (ptnode)malloc(sizeof(tnode));
            if (p->right == NULL) {
                printf("memory alloc error.\n");
            }
            p->right->sortkeys = NULL;
            p->right->data = NULL;
            p->right->left = NULL;
            p->right->right = NULL;
        }
        addtnode(p->right, sortkey, data, obj);
    }
}

void debug_tree(ptnode root, char c) {
    if (root->left != NULL) {
        debug_tree(root->left, 'l');
    }
    printf("%c[%s].\n", c, root->sortkeys);
    if (root->right != NULL) {
        debug_tree(root->right, 'r');
    }
}

void out_tree(ptnode p, FILE * out_temp) {
    int write_cnt;
    plist pe = NULL;
    if (p->left != NULL) {
        out_tree(p->left, out_temp);
    }

    pe = p->data;
    while(pe != NULL) {
        write_cnt = fwrite(pe->data, sizeof(char), strlen(pe->data), out_temp);
        pe = pe->next;
    }

    if (p->right != NULL) {
        out_tree(p->right, out_temp);
    }
}

void free_list2(plist p) {
    plist pe = NULL;
    plist pre = NULL;
    if (p->next != NULL) {
        pre = pe;
        pe = p->next;
        free_list2(pe);
    }
    if (p->data != NULL) {
        free(p->data);
        p->data = NULL;
    }
    free(p);
    p = NULL;
}

void free_list(plist pre, plist cur) {
    if (cur->next != NULL) {
        free_list(cur, cur->next);
    }
    if (cur->data != NULL) {
        free(cur->data);
        cur->data = NULL;
    }
    free(cur);
    cur = NULL;
    pre->next = NULL;
}

void free_tree(ptnode p) {
    if (p->left != NULL) {
       free_tree(p->left);
       p->left = NULL;
    }

    if (p->right != NULL) {
       free_tree(p->right);
       p->right = NULL;
    }

    if (p->left == NULL && p->right == NULL) {
        if (p->data != NULL) {
            free_list(p->data, p->data->next);
            if (p->data->data != NULL) {
                free(p->data->data);
                p->data->data = NULL;
            }
            free(p->data);
            p->data = NULL;
        }
        if (p->sortkeys != NULL) {
            free(p->sortkeys);
            p->sortkeys = NULL;
        }
        free(p);
        p = NULL;
    }
}

int main_proc(pdatamodel obj) {
    int i;
    int pid = getpid();
    int memory_size = 52428800;
/*     int memory_size = 1024; */
    int pool_size;
    int max_cnt = memory_size / (obj->rec_len + obj->key_len + 1);
    int max_pool_cnt;
    int cnt = 0;
    char * buf = NULL;
    char * read_buf = NULL;
    char * sortkey_buf = NULL;
    size_t read_cnt;
    size_t write_cnt;
    FILE * out_temp;
    FILE * tempfile[MAX_IN_FILE_CNT];
    char * temp_buf[MAX_IN_FILE_CNT];
    char * sortkey = NULL;
    char * min_sortkey;
    int    min_tempfile;
    char * max_sortkey;
    char out_temp_name[200];
    int out_temp_cnt;
    int read_flag;
    int out_flag;
    int min_flag;

    tnode root;
    root.sortkeys = NULL;
    root.data = NULL;
    root.left = NULL;
    root.right = NULL;

printf("pid:%d.\n", pid);
    memset(out_temp_name, 0, sizeof(out_temp_name));
    buf = (char *)malloc(sizeof(char) * (obj->rec_len + 1) * max_cnt);
    if (buf == NULL) {
        printf("memory alloc error.\n");
        return -1;
    }
    read_buf = (char *)malloc(sizeof(char) * (obj->rec_len + obj->key_len + 1));
    if (read_buf == NULL) {
        printf("memory alloc error.\n");
        return -1;
    }
    sortkey_buf = (char *) malloc(sizeof(char) * (obj->key_len + 1));

    /* rewrite input file by memory size and inner sort */
    cnt = 0;
    out_temp_cnt = 1;
    memset(out_temp_name, 0, sizeof(out_temp_name));
    sprintf(out_temp_name, "./sort_%d_%d", pid, out_temp_cnt);
    out_temp = fopen(out_temp_name, "wb+");
    for (i = 0; i < obj->infile_cnt ; i ++) {
        if (obj->pin_file[i] != NULL) {

            while(!feof(obj->pin_file[i])) {
                memset(read_buf, 0 , sizeof(char) * (obj->rec_len + obj->key_len + 1));
                read_cnt = fread(read_buf, sizeof(char), obj->rec_len, obj->pin_file[i]);
                if (read_cnt < obj->rec_len) {
                    break;
                }
/*                printf("%s", read_buf); */
                cnt ++;
                if (cnt >= max_cnt) {
                   out_tree(&root, out_temp);
                   fclose(out_temp);

                   free_tree(&root);
                   root.sortkeys = NULL;
                   root.data = NULL;
                   root.left = NULL;
                   root.right = NULL;

                   cnt = 0;
                   out_temp_cnt ++;
                   memset(out_temp_name, 0, sizeof(out_temp_name));
                   sprintf(out_temp_name, "./sort_%d_%d", pid, out_temp_cnt);
                   out_temp = fopen(out_temp_name, "wb+");
                }
                memset(sortkey_buf, 0, sizeof(char) * (obj->key_len + 1));
                getsortkey(sortkey_buf, read_buf, obj);
/* printf("sort key:[%s].\n", sortkey_buf); */
                addtnode(&root, sortkey_buf, read_buf, obj);
                /* write_cnt = fwrite(read_buf, sizeof(char), read_cnt, out_temp); */

            }

        }
    }
/*     debug_tree(&root, 'c'); */
    out_tree(&root, out_temp);
    fclose(out_temp);

/* printf("file cnt:%d.\n", out_temp_cnt); */
    if (out_temp_cnt > MAX_IN_FILE_CNT) {
         printf("file 's count error.\n");
    }


    /* merge rewroten file and write out them. */


    pool_size = memory_size / (out_temp_cnt + 1);
    max_pool_cnt = pool_size / obj->rec_len;
/* printf("file cnt:[%d].\n", out_temp_cnt); */
    for (i = 0; i < out_temp_cnt; i ++) {
        memset(out_temp_name, 0, sizeof(out_temp_name));
        sprintf(out_temp_name, "./sort_%d_%d", pid, (i + 1));
        tempfile[i] = fopen(out_temp_name, "rb");
        if (tempfile[i] == NULL) {
            printf("temp file open error.\n");
        } else {
            /* printf("temp file %d is opened.\n", i + 1); */
        }
        temp_buf[i] = (char *)malloc(sizeof(char)*(obj->rec_len + 1));
        if (temp_buf[i] == NULL) {
            printf("memory alloc error.\n");
        } else {
            /* printf("temp file %d's buffer is alloced.\n", i + 1); */
        }
        memset(temp_buf[i], 0, obj->rec_len + 1);
        read_cnt = fread(temp_buf[i], sizeof(char), obj->rec_len, tempfile[i]);
        if (i == 0) {
            min_sortkey = (char *)malloc(sizeof(char)*(obj->key_len + 1));
            if (min_sortkey == NULL) {
                printf("memory alloc error.\n");
            }
            memset(min_sortkey, 0, obj->key_len + 1);
            getsortkey(min_sortkey, temp_buf[i], obj);
            min_tempfile = (i + 1);
        } else {
            sortkey = (char *)malloc(sizeof(char)*(obj->key_len + 1));
            if (sortkey == NULL) {
                printf("memory alloc error.\n");
            }
            memset(sortkey, 0, obj->key_len + 1);
            getsortkey(sortkey, temp_buf[i], obj);
            /* if (memcmp(sortkey, min_sortkey, obj->key_len) < 0) { */
            if (keycmp(sortkey, min_sortkey, obj) < 0) {
                memset(min_sortkey, 0, sizeof(char)*(obj->key_len + 1));
                memcpy(min_sortkey, sortkey, obj->key_len);
                min_tempfile = (i + 1);
            }
        }
    }
/* printf("temp file's count:[%d].\n", out_temp_cnt); */

    obj->pout_file = fopen(obj->outfile_name, "wb");
    if (obj->pout_file == NULL) {
        printf("output file open error.\n");
    }
/*     printf("[INFO]outfile[%s] is opened.\n", obj->outfile_name); */

    out_flag = 1;
    while(out_flag) {
/* printf("%d file is writing ...\n", min_tempfile); */
        write_cnt = fwrite(temp_buf[min_tempfile - 1], sizeof(char), strlen(temp_buf[min_tempfile - 1]), obj->pout_file);
        if (write_cnt != strlen(temp_buf[min_tempfile - 1])) {
            printf("write error.\n");
        }
/* printf("[INFO]%s is wroten.(%d)\n", temp_buf[min_tempfile - 1], min_tempfile); */
        read_flag = 1;
        while(read_flag) {
            if (tempfile[min_tempfile - 1] == NULL) {
                break;
            }
            if (temp_buf[min_tempfile - 1] == NULL) {
                break;
            }
            memset(temp_buf[min_tempfile - 1], 0, obj->rec_len + 1);
            read_cnt = fread(temp_buf[min_tempfile - 1], sizeof(char), obj->rec_len, tempfile[min_tempfile - 1]);
/* printf("[INFO] next %d is readed.[%s]\n", read_cnt, temp_buf[min_tempfile - 1]); */
            if (read_cnt < obj->rec_len || feof(tempfile[min_tempfile - 1])) {
/* printf("%d file is end.\n", min_tempfile); */
                fclose(tempfile[min_tempfile - 1]);
                tempfile[min_tempfile - 1] = NULL;
                free(temp_buf[min_tempfile - 1]);
                temp_buf[min_tempfile - 1] = NULL;
                break;
            }
            if (sortkey == NULL) {
                printf("sortkey is null.\n");
                sortkey = (char *)malloc(sizeof(char)*(obj->key_len + 1));
                if (sortkey == NULL) {
                    printf("memory alloc error.\n");
                }
            } else {
/*                 printf("sortkey is %s.\n", sortkey); */
            }
            memset(sortkey, 0, obj->key_len + 1);
            getsortkey(sortkey, temp_buf[min_tempfile - 1], obj);
/* printf("next sort key[%s] vs [%s].\n", sortkey, min_sortkey); */
            /* if (memcmp(sortkey, min_sortkey, obj->key_len) == 0) { */
            if (keycmp(sortkey, min_sortkey, obj) == 0) {
                 write_cnt = fwrite(temp_buf[min_tempfile - 1], sizeof(char), strlen(temp_buf[min_tempfile - 1]), obj->pout_file);
                 if (write_cnt != strlen(temp_buf[min_tempfile - 1])) {
                    printf("write error.\n");
                 }
            } else {
                read_flag = 0;
            }
        }

        // �悭�Ȃ�����
        min_flag = 0;
/* printf("temp file's count:%d.\n", out_temp_cnt); */
        for (i = 0; i < out_temp_cnt; i ++) {
            if (temp_buf[i] == NULL) {
                continue;
            }
/* printf("%d buf:%s.\n", i, temp_buf[i]); */
            if (min_flag == 0) {
                memset(min_sortkey, 0, obj->key_len + 1);
                getsortkey(min_sortkey, temp_buf[i], obj);
/* printf("min[%s].\n", min_sortkey); */
                min_tempfile = (i + 1);
                min_flag = 1;
            } else {
                memset(sortkey, 0, obj->key_len + 1);
                getsortkey(sortkey, temp_buf[i], obj);
/* printf("%s vs %s.\n", sortkey, min_sortkey); */
                /* if (memcmp(sortkey, min_sortkey, obj->key_len) < 0) { */
                if (keycmp(sortkey, min_sortkey, obj) < 0) {
                    memset(min_sortkey, 0, sizeof(char)*(obj->key_len + 1));
                    memcpy(min_sortkey, sortkey, obj->key_len);
                    min_tempfile = (i + 1);
                }
            }
        }
        if (min_flag == 0) {
            break;
        }
    }

    if (obj->pout_file != NULL) {
        fclose(obj->pout_file);
        obj->pout_file = NULL;
    }
    printf("[INFO]outfile has closed.\n");

    for (i = 0; i < out_temp_cnt; i ++) {
        if (tempfile[i] != NULL) {
            fclose(tempfile[i]);
            tempfile[i] = NULL;
        }
        if (temp_buf[i] != NULL) {
            free(temp_buf[i]);
            temp_buf[i] = NULL;
        }
    }


    if (sortkey != NULL) {
         free(sortkey);
         sortkey = NULL;
    }

    if (min_sortkey != NULL) {
         free(min_sortkey);
         min_sortkey = NULL;
    }

    if (buf != NULL) {
        free(buf);
        buf = NULL;
    }

    if (read_buf != NULL) {
        free(read_buf);
        read_buf = NULL;
    }

    return 0;
}

int releasememory(pdatamodel obj) {
    int i;
    for(i = 0; i < obj->param_cnt; i ++) {
        if (obj->param[i] != NULL) {
            free(obj->param[i]);
            obj->param[i] = NULL;
        }
    }
    obj->param_cnt = 0;
    for(i = 0; i < obj->param_sum_cnt; i ++) {
        if (obj->param_sum[i] != NULL) {
            free(obj->param_sum[i]);
            obj->param_sum[i] = NULL;
        }
    }
    obj->param_sum_cnt = 0;
    
    for (i = 0; i < obj->infile_cnt; i ++) {
        if (obj->pin_file[i] != NULL) {
            fclose(obj->pin_file[i]);
            obj->pin_file[i] = NULL;
        }
        if (obj->infile_name[i] != NULL) {
            free(obj->infile_name[i]);
            obj->infile_name[i] = NULL;
        }
    }
    if (obj->outfile_name != NULL) {
        free(obj->outfile_name);
        obj->outfile_name = NULL;
    }
    return 0;
}

int main(int argc, char * argv[]) {
    datamodel pam;
    int r;
    /* MEMORYSTATUS status; */
    time_t timer;
    struct tm * timeinfo;

    pam.param_cnt = 0;
    pam.param_sum_cnt = 0;
    pam.infile_cnt = 1;
    pam.sort_key_cnt = 0;
    pam.sum_item_cnt = 0;

    r = 0;

    /* mtrace(); */
    /* GlobalMemoryStatus(&status);
    printf("%ld / %ld.(%d)\n", status.dwAvailPhys / 1024, status.dwTotalPhys / 1024, status.dwMemoryLoad); */ //
    printf("%s begin.\n", argv[0]);
    time(&timer);
    timeinfo = localtime(&timer);
    printf ("Current local time and date: %s", asctime(timeinfo));

    r = getsettings(&pam);
    debug_settings(&pam);
    if (r == 0 ) {
        r = chksettings(&pam);
    }
    debug_settings(&pam);
    if (r == 0 ) {
        main_proc(&pam);
    }

    releasememory(&pam);

    time(&timer);
    timeinfo = localtime(&timer);
    printf ("Current local time and date: %s", asctime(timeinfo));

    if (r == 0 ) {
        printf("%s normal end.\n", argv[0]);
    } else {
        printf("%s is abend.\n", argv[0]);
    }
    /* GlobalMemoryStatus(&status);
    printf("%ld / %ld.(%d)\n", status.dwAvailPhys / 1024, status.dwTotalPhys / 1024, status.dwMemoryLoad); */ // dwMemoryLoad

    /* muntrace(); */
    return r;
}
