#include "heap.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

static inline int parent(int i) {
	return i/2;
}

static inline int left(int i) {
	return i*2;
}

static inline int right(int i) {
	return i*2 + 1;
}

int* heapify(void* data, int index, int e_size, int size, int (*comparator)(void*, void*), int* results) {
	int l = left(index);
	int r = right(index);
	int largest;
	
	if (l <= size) {
		results[0]++;
		if ((*comparator)(data + l*e_size, data + r*e_size) > 0) largest = l;
		else largest = index;
	} else largest = index;
	
	if (r <= size) {
		results[0]++;
		if ((*comparator)(data + r*e_size, data + largest*e_size) > 0) largest = r;
	}
	
	if (largest != index) {
		results[1]++;
		void* temp = malloc(e_size);
		memcpy(temp, data + index*e_size, e_size);
		memcpy(data + index*e_size, data + largest*e_size, e_size);
		memcpy(data + largest*e_size, temp, e_size);
		free(temp);
		
		heapify(data, largest, e_size, size, comparator, results);
	}
}

int* heap_sort(void* data, int e_size, int size, int (*comparator)(void*, void*)) {
	int* results = malloc(2*sizeof(int));
	results[0] = 0;
	results[1] = 0;

	for (int i = size/2; i >= 0; i--) heapify(data, i, e_size, size, comparator, results);
	
	for (int i = size; i >= 1; i--) {
		results[1]++;
		void* temp = malloc(e_size);
		memcpy(temp, data, e_size);
		memcpy(data, data + i*e_size, e_size);
		memcpy(data + i*e_size, temp, e_size);
		free(temp);
		
		heapify(data, 0, e_size, --size, comparator, results);
	}

	return results;
}