#include "quick.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

int partition(int* data, int lo, int hi, int e_size, int (*comparator)(void*, void*), int* results) {
	int i = lo;
	void* pivot = data + hi*e_size;
	for (int j = lo; j < hi; j++) {
		results[0]++;
		if ((*comparator)(data + j*e_size, pivot) < 0) {
			results[1]++;
			void* temp = malloc(e_size);
			memcpy(temp, data + i*e_size, e_size);
			memcpy(data + i*e_size, data + j*e_size, e_size);
			memcpy(data + j*e_size, temp, e_size);
			free(temp);
			
			i++;
		}
	}
	
	results[1]++;
	void* temp = malloc(e_size);
	memcpy(temp, data + i*e_size, e_size);
	memcpy(data + i*e_size, data + hi*e_size, e_size);
	memcpy(data + hi*e_size, temp, e_size);
	free(temp);
	
	return i;
}


void qs(void* data, int lo, int hi, int e_size, int (*comparator)(void*, void*), int* results) {
	if (lo < hi) {
		int p = partition(data, lo, hi, e_size, comparator, results);
		qs(data, lo, p - 1, e_size, comparator, results);
		qs(data, p + 1, hi, e_size, comparator, results);
	}
}

int* quick_sort(void* data, int e_size, int size, int (*comparator)(void*, void*)) {
	int* results = malloc(2*sizeof(int));
	results[0] = 0;
	results[1] = 0;
	
	qs(data, 0, size - 1, e_size, comparator, results);
	
	return results;
}