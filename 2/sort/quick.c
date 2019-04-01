#include "quick.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

int partition(void* data, int lo, int e_size, int hi, int (*comparator)(void*, void*), int* results) {
	int i = lo - 1;
	
	for (int j = lo; j <= hi - 1; j++) {
		results[0]++;
		if ((*comparator)(data + j*e_size, data + hi*e_size) <= 0) {
			results[1]++;
			i++;
			void* temp = malloc(e_size);
			memcpy(temp, data + i*e_size, e_size);
			memcpy(data + i*e_size, data + j*e_size, e_size);
			memcpy(data + j*e_size, temp, e_size);
			free(temp);
		}
	}
	results[1]++;
	void* temp = malloc(e_size);
	memcpy(temp, data + (i+1)*e_size, e_size);
	memcpy(data + (i+1)*e_size, data + hi*e_size, e_size);
	memcpy(data + hi*e_size, temp, e_size);
	free(temp);

	return i+1;
}

int q(void* data, int index, int e_size, int size, int (*comparator)(void*, void*), int* results) {
	if (index < size) {
		int m = partition(data, index, e_size, size, comparator, results);
		q(data, index, e_size, m-1, comparator, results);
		q(data, m+1, e_size, size, comparator, results);
	}
}

int* quick_sort(void* data, int e_size, int size, int (*comparator)(void*, void*)) {
	int* results = malloc(2*sizeof(int));
	results[0] = 0;
	results[1] = 0;
	
	q(data, 0, e_size, size, comparator, results);
	
	return results;
}