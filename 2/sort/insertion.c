#include "insertion.h"
#include <string.h>
#include <stdlib.h>

int* insertion_sort(void* data, int e_size, int size, int (*comparator)(void*, void*)) {
	int* results = malloc(2*sizeof(int));
	results[0] = 0;
	results[1] = 0;
	
	for (int i = 0; i < size; i++) {
		int j = i;
		while (j > 0) {
			results[0]++;
			if ((*comparator)(data + (j-1)*e_size, data + j*e_size) > 0) {
				results[1]++;
				void* temp = malloc(e_size);
				memcpy(temp, data + j*e_size, e_size);
				memcpy(data + j*e_size, data + (j-1)*e_size, e_size);
				memcpy(data + (j-1)*e_size, temp, e_size);
				free(temp);
				j--;
			} else break;
		}
	}
	
	return results;
}