#include "heap.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

static inline void heapify(void* data, int index, int e_size, int size, int (*comparator)(void*, void*), int* results) {
	int l = 2*index;
	int r = 2*index + 1;
	int largest = index;
	
	if (l < size) {
		results[0]++;
		if ((*comparator)(data + l*e_size, data + index*e_size) > 0) largest = l;
	}
	
	if (r < size) {
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

	for (int i = size - 1; i >= 0; i--) {
		
		results[1]++;
		void* temp = malloc(e_size);
		memcpy(temp, data, e_size);
		memcpy(data, data + i*e_size, e_size);
		memcpy(data + i*e_size, temp, e_size);
		free(temp);
		
		size--;
		heapify(data, 0, e_size, size, comparator, results);
	}

	return results;
}