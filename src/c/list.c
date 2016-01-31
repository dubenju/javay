#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct _node {
    char * data;
    struct _node * next;
} node, * pnode;

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
