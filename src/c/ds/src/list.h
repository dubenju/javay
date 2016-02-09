/*
 * list
 *
 *  Created on: 2016年2月3日
 *      Author: dubenju
 */

#ifndef LIST_H_
#define LIST_H_

#include<stdio.h>
#include<stdlib.h>

typedef struct node{
    int data;
    struct node *next;
}LNode, *LinkList;

LinkList init( int len );
void printLink( LinkList l );
void insertEle( LinkList l, int n, int e );
void updateNode( LinkList l, int n, int e );
void delNode( LinkList l, int n );

#endif /* LIST_H_ */
