#include <stdlib.h>
#include <stdio.h>
#include <string.h>

typedef struct {
	void* data;
	struct LNode* next;
} LNode;

typedef struct {
	LNode* first;
} LList;


//prints the list as integers (for debug)
void Lprint(LList* list) {
	LNode* node = list-> first;
	printf("printinf the list:\n");
	
	while(node != NULL) {
		int d = *(int*) node->data;
		printf("%d -> ", d);
		
		node = node->next;
	}
	
	printf("NULL\n");
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
int iinsert(LList* list, void* data, size_t datasize, int index) {
	LNode* node = list->first;

	for (int i = 0; i < index; i++) {
		if (node == NULL) return 0;
	
		node = node->next;
	}
	
	LNode* newnode = malloc(sizeof(LNode));
	void* allocated = malloc(datasize);
	
	memcpy(allocated, data, datasize);
	
	newnode->next = node->next;
	newnode->data = allocated;
	
	node->next = newnode;
	
	return 1;
}

//deletes some specific data from the list
//returns 0 if failed to do so (there was no such data)
//returns 1 if did so
int del(LList* list, void* data, size_t datasize) {
	LNode* node = list->first;
	LNode* prev;
	
	while(node != NULL) {
		if (memcmp(data, node->data, datasize) == 0) {
			
			if (prev != NULL) {
				prev->next = node->next;
			} else {
				list->first = node->next;
			}
			
			free(node->data);
			free(node);
			
			return 1;
		}
		
		prev = node;
		node = node->next;
	}
	
	return 0;
}

//finds some specific data from the list and moves it to the beginning
//returns 0 if failed to do so (there was no such data)
//returns 1 if did so
int findMTF(LList* list, void* data, size_t datasize) {
	LNode* node = list->first;
	LNode* prev;
	
	while(node != NULL) {
		if (memcmp(data, node->data, datasize) == 0) {
			if (prev != NULL) {
				prev->next = node->next;
				node->next = list->first;
				list->first = node;
			}
			
			return 1;
		}
		
		prev = node;
		node = node->next;
	}
	
	return 0;
}

//finds some specific data from the list and moves it one place up
//returns 0 if failed to do so (there was no such data)
//returns 1 if did so
int findTRANS(LList* list, void* data, size_t datasize) {
	LNode* node = list->first;
	LNode* prev;
	LNode* pprev;
	
	while(node != NULL) {
		if (memcmp(data, node->data, datasize) == 0) {
			if (prev != NULL) {
				if (pprev != NULL) {
					pprev->next = node;
					prev->next = node->next;
					node->next = prev;
				} else {
					list->first = node;
					prev->next = node->next;
					node->next = prev;
				}
			}
			
			return 1;
		}
		
		pprev = prev;
		prev = node;
		node = node->next;
	}
	
	return 0;
}

int main(void) {
	LList list;
	list.first = NULL;
	
	Lprint(&list);
	
	int a[5] = {476, 2137, 621, 1337, 0};
	
	insert(&list, &a[0], sizeof(int));
	insert(&list, &a[1], sizeof(int));
	insert(&list, &a[2], sizeof(int));
	insert(&list, &a[3], sizeof(int));
	insert(&list, &a[4], sizeof(int));
	
	Lprint(&list);
	
	del(&list, &a[1], sizeof(int));
	
	Lprint(&list);

	findMTF(&list, &a[2], sizeof(int));

	Lprint(&list);
	
	findTRANS(&list, &a[3], sizeof(int));
	
	Lprint(&list);

	return 0;
}