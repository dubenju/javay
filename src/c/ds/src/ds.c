/*
 ============================================================================
 Name        : ds.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include "list.h"

int main(void) {
	puts("!!!Hello World!!!"); /* prints !!!Hello World!!! */

	LinkList l;
	l = init(5);
	printLink(l);
	insertEle(l, 6, 10);
	printLink(l);
	delNode(l, 2);
	printLink(l);
	updateNode(l, 6, 22);
	printLink(l);
	printf("this is a test for list by c./n");

	return EXIT_SUCCESS;
}
