/*
 * list.c
 *
 *  Created on: 2016年2月3日
 *      Author: dubenju
 */
#include "list.h"

/**
 * 初始化并创建一个带头节点的链表
 **/
LinkList init( int len ) {
    int i;
    LinkList p, r, list;
    list = (LinkList) malloc(sizeof(LNode));
    list->next = NULL;
    for ( i = 0; i < len; i++ ) {
        p = (LinkList) malloc(sizeof(LNode));
        p->data = i+1;
        p->next = NULL;
        if ( !list->next ) {
            list->next = p;  //赋值头节点
        } else {
            r->next = p;
        }
        r = p;  //移动r指针,指向下一个节点
    }
    return list;
}

/**
 * 插入元素
 * @param int n 插入的位置
 * @param int e 插入的值
 **/
void insertEle( LinkList l, int n, int e ) {
    LinkList p, q, ele;
    int i = 1;
    ele = (LinkList)malloc(sizeof(LNode));
    ele->data = e;
    ele->next = NULL;
    p = q = l->next;

    if ( n == 1 ) {         //将元素添加到链表头
        l->next = ele;
        ele->next = q;
        return;
    }
    while ( i != n && q->next != NULL ) {
        p = q;
        q = q->next;
        i++;
    }
    if ( i == n ) {
        p->next = ele;
        ele->next = q;
    } else if( i < n ) { //将元素添加到链表尾
        q->next = ele;
    }
}

/**
 * 删除节点
 * @param int n 欲删除节点的位置
 **/
void delNode( LinkList l, int n ) {
    LinkList p, q;
    int i = 1;
    p = q = l->next;
    if ( n == 1 ) {
        l->next = q->next;
    } else {
        while ( i != n && q->next != NULL ) {
            p = q;
            q = q->next;
            i++;
        }
        if ( i == n ) {
            p->next = q->next;
        }
    }
}

/**
 * 修改节点
 * @param int n 欲修改节点的位置
 * @param int e 修改值
 **/
void updateNode( LinkList l, int n, int e ) {
    LinkList q;
    int i = 1;
    q = l->next;
    while ( i != n && q->next != NULL ) {
        q = q->next;
        i++;
    }
    if ( i == n ) {
        q->data = e;
    }
}

/**
 * 打印链表
 **/
void printLink( LinkList l ) {
    LinkList q;
    q = l->next;
    while ( q->next != NULL ) {
        printf("%d ", q->data);
        q = q->next;
    }
    printf("%d\n", q->data); //打印最后一个元素
}
