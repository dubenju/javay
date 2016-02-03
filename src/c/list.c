#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* 1.初始化线性表，即置单链表的表头指针为空 */
/* 2.创建线性表，此函数输入负数终止读取数据*/
/* 3.打印链表，链表的遍历*/
/* 4.清除线性表L中的所有元素，即释放单链表L中所有的结点，使之成为一个空表 */
/* 5.返回单链表的长度 */
/* 6.检查单链表是否为空，若为空则返回１，否则返回０ */
/* 7.返回单链表中第pos个结点中的元素，若pos超出范围，则停止程序运行 */
/* 8.从单链表中查找具有给定值x的第一个元素，若查找成功则返回该结点data域的存储地址，否则返回NULL */
/* 9.把单链表中第pos个结点的值修改为x的值，若修改成功返回１，否则返回０ */
/* 10.向单链表的表头插入一个元素 */
/* 11.向单链表的末尾添加一个元素 */
/* 12.向单链表中第pos个结点位置插入元素为x的结点，若插入成功返回１，否则返回０ */
/* 13.向有序单链表中插入元素x结点，使得插入后仍然有序 */
/* 14.从单链表中删除表头结点，并把该结点的值返回，若删除失败则停止程序运行 */
/* 15.从单链表中删除表尾结点并返回它的值，若删除失败则停止程序运行 */
/* 16.从单链表中删除第pos个结点并返回它的值，若删除失败则停止程序运行 */
/* 17.从单链表中删除值为x的第一个结点，若删除成功则返回1,否则返回0 */
/* 18.交换2个元素的位置 */
/* 19.将线性表进行快速排序 */

typedef struct _node {
    char * data;
    struct _node * next;
} node, * pnode;

/* 1.初始化线性表，即置单链表的表头指针为空 */
void initList(pnode *pNode) {
    *pNode = NULL;
    printf("initList函数执行，初始化成功\n");
}

void add_node(pnode cur, char * buf) {
    if (cur->next != NULL) {
        add_node(cur->next, buf);
    } else {
        cur->next = (pnode) malloc(sizeof(node));
        if (cur->next == NULL) {
            printf("memory alloc error.\n");
        }
        cur->next->data = (char *) malloc(sizeof(char) * (strlen(buf) + 1));
        if (cur->next->data == NULL) {
            printf("memory alloc error.\n");
        }
        memset(cur->next->data, 0, sizeof(char) * (strlen(buf) + 1));
        sprintf(cur->next->data, "%s", buf);
        cur->next->next = NULL;
    }
}

void debug_node(pnode cur) {
    printf("%s.\n", cur->data);
    if (cur->next != NULL) {
        debug_node(cur->next);
    }
}

void free_node(pnode pre, pnode cur) {
    if (cur->next != NULL) {
        free_node(cur, cur->next);
    }
    if (cur->data != NULL) {
        free(cur->data);
        cur->data = NULL;
    }
    free(cur);
    cur = NULL;
    pre->next = NULL;
}

int main(int argc, char * argv[]) {
    pnode root;
    root = (pnode) malloc(sizeof(node));
    if (root == NULL) {
        printf("memory alloc error.\n");
    }
    root->data = (char *) malloc(sizeof(char)  * 5);
    if (root->data == NULL) {
        printf("memory alloc error.\n");
    }
    memset(root->data, 0, 5);
    sprintf(root->data, "root");
    root->next = NULL;

    add_node(root, "0001");
    add_node(root, "0002");
printf("------------test.\n");
    debug_node(root);

    free_node(root, root->next);

    if (root != NULL) {
        if (root->data != NULL) {
            _free_dbg(root->data, _CLIENT_BLOCK);
            root->data = NULL;
        }
        free(root);
        root = NULL;
    }
    return 0;
}
