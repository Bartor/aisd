#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>

typedef struct LNode {
	void* data;
	struct LNode* next;
} LNode;

typedef struct {
	LNode* first;
	//count the comparisons made
	int mtf;
	int trans;
} LList;

//prints the list as integers (for debug)
void printInt(LList* list) {
	LNode* node = list->first;

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
	LNode* prev = NULL;
	
	while(node != NULL) {
		
		//both methods compare every element while deleting
		list->mtf++;
		list->trans++;
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
	LNode* prev = NULL;
	
	while(node != NULL) {
		list->mtf++;
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
	LNode* prev = NULL;
	LNode* pprev = NULL;
	
	while(node != NULL) {
		list->trans++;
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
	//tested array size
	const int SIZE = 100;
	
	int tab[SIZE];
	for (int i = 0; i < SIZE; i++) tab[i] = i+1;
	
	//shuffle the array
	srand(time(NULL));
	for (int i = 0; i < SIZE - 1; i++) {
		int index = rand() % SIZE, e = tab[i];
		
		tab[i] = tab[index];
		tab[index] = e;
	}
	
	LList m = {NULL, 0};
	LList t = {NULL, 0};
	
	for (int i = 0; i < SIZE; i++) {
		insert(&m, &tab[i], sizeof(int));
		insert(&t, &tab[i], sizeof(int));
	}
	
	for (int j = SIZE; j > 0; j--) {
		for (int i = 1; i <= SIZE; i++) {
			findTRANS(&t, &i, sizeof(int));
			findMTF(&m, &i, sizeof(int));
		}
		del(&t, &j, sizeof(int));
		del(&m, &j, sizeof(int));
	}

	printf("Total comparisons done\nmtf:  %10d of which del did:%6d\ntrans:%10d of which del did:%6d\n", m.mtf, m.trans, t.trans, t.mtf);

	return 0;
}
