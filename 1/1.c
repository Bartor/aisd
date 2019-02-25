#include <stdlib.h>
#include <stdio.h>

typedef struct {
	LNode* first;
} LList;

typedef struct {
	void* data;
	LNode* next;
} LNode;

//utility to find some data in the list
//returns index of first occurance of the data on the list
//return -1 if not found
int find(LList* list, void* data, size_t datasize) {
	LNode* node = list->first;
	int i = -1;
	
	while (node != NULL) {
		i++;
		if (memcmp(data, node->data, datasize) == 0) return i;
	}
	return i;
}

int isempty(LList* list) {
	//we can do return list->first == NULL but we want to make sure true is 1 and false is 0
	if (list->first == NULL) return 1;
	return 0;
}

//inserts at the beginning of the list
int insert(LList* list, void* data, size_t datasize) {
	LNode* node = malloc(sizeof(LNode));
	void* allocated = malloc(datasize);
	
	memcpy(allocated, data, datasize);
	
	node->next = list->first;
	node->data = allocated;
	
	list->first = node;
	
	return 1;
}

//inserts at the index of the list
//returns 0 if failed to do so
//returns 1 if did so
int insert(LList* list, void* data, size_t datasize, int index) {
	LNode* node = list->first;
	int i = 0;

}