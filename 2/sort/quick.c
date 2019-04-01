#include "quick.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

void q(void* data, int l, int r, int e_size, int (*comparator)(void*, void*), int* results) {
	if (l >= r) return;
	int i = l;
	int j = r;
	
	void* pivot = malloc(e_size);
	memcpy(pivot, data + i*e_size, e_size);
	
	while (1) {
		results[0] += 2;
		while((*comparator)(data + i*e_size, pivot) < 0) {
			results[0]++;
			i++;
		}
		while((*comparator)(pivot, data + j*e_size) < 0) {
			results[0]++;
			j--;
		}
		
		if (i >= j) break;
		
		results[1]++;
		void* temp = malloc(e_size);
		memcpy(temp, data + i*e_size, e_size);
		memcpy(data + i*e_size, data + j*e_size, e_size);
		memcpy(data + j*e_size, temp, e_size);
		free(temp);
	}
	
	q(data, l, i-1, e_size, comparator, results);
	q(data, j+1, r, e_size, comparator, results);
}

int* quick_sort(void* data, int e_size, int size, int (*comparator)(void*, void*)) {
	int* results = malloc(2*sizeof(int));
	results[0] = 0;
	results[1] = 0;
	
	q(data, 0, size-1, e_size, comparator, results);
	
	return results;
}