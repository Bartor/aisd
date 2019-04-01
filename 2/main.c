#include "sort/heap.h"
#include <stdio.h>
#include <stdlib.h>

static inline int compare_int_desc(void* a, void* b) {
	return *(int*)b - *(int*)a;
}

static inline int compare_int_asc(void* a, void* b) {
	return *(int*)a - *(int*)b;
}

int main(void) {
	int arr[19] = {4, 3, 2, 1, 5, 12, 3, 2, 43, 2, 13, 4, 2, 45, 65, 43, 2, 134, 3};
	
	int* res = heap_sort(arr, sizeof(int), 19, &compare_int_desc);
	
	for (int i = 0; i < 19; i++) printf("%d ", arr[i]);
	printf("\ncomparisons: %d swaps: %d\n", res[0], res[1]);
	
	free(res);
	
	return 0;
}