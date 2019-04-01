#include "sort/selection.h"
#include <stdio.h>

static inline int compare_int_desc(void* a, void* b) {
	return *(int*)b - *(int*)a;
}

static inline int compare_int_asc(void* a, void* b) {
	return *(int*)a - *(int*)b;
}

int main(void) {
	int arr[5] = {4, 3, 2, 1, 5};
	
	int* res = selection_sort(arr, sizeof(int), 5, &compare_int_asc);
	
	for (int i = 0; i < 5; i++) printf("%d ", arr[i]);
	printf("comparisons: %d swaps: %d\n", res[0], res[1]);
	
	return 0;
}