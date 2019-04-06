#include "quick.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

int partition(int* data, int p, int r, int e_size, int (*comparator)(void*, void*), int* results) {
	int j, i;
	i = p - 1;
	void* x = data + r*e_size;
	for (j = p; j < r; j++) {
		results[0]++;
		if ((*comparator)(data + j*e_size, x) <= 0) {
			i++;
			results[1]++;
			void* temp = malloc(e_size);
			memcpy(temp, data + i*e_size, e_size);
			memcpy(data + i*e_size, data + j*e_size, e_size);
			memcpy(data + j*e_size, temp, e_size);
			free(temp);
		}
	}
	
	memcpy(data + r*e_size, data + (i+1)*e_size, e_size);
	memcpy(data + (i+1)*e_size, x, e_size);
	
	return i+1;
}


void qs(void* data, int p, int r, int e_size, int (*comparator)(void*, void*), int* results) {
	if (p < r) {
		int q = partition(data, p, r, e_size, comparator, results);
		qs(data, p, q - 1, e_size, comparator, results);
		qs(data, q + 1, r, e_size, comparator, results);
	}
}

int* quick_sort(void* data, int e_size, int size, int (*comparator)(void*, void*)) {
	int* results = malloc(2*sizeof(int));
	results[0] = 0;
	results[1] = 0;
	
	qs(data, 0, size - 1, e_size, comparator, results);
	
	return results;
}