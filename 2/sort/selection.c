#include "selection.h"
#include <string.h>
#include <stdlib.h>

int selection_sort(void* data, int e_size, int size, int (*comparator)(void*, void*)) {
	for (int i = 0; i < size; i++) {
		int min = i;
		
		for (int j = i+1; j < size; j++) {
			if ((*comparator)(data+j*e_size, data + min*e_size) < 0) min = j;
		}
		
		if (min != i) {
			void* temp = malloc(e_size);
			memcpy(temp, data + i*e_size, e_size);
			memcpy(data + i*e_size, data + min*e_size, e_size);
			memcpy(data + min*e_size, temp, e_size);
			free(temp);
		}
	}
}