#include "mquick.h"
#include "insertion.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

static int median (void* data, int lo, int hi, int e_size, int (*comparator)(void*, void*), int* results) {
	int mid = (lo+hi)/2;
	results[0]++;
	if ((*comparator)(data + lo*e_size, data+hi*e_size) < 0) {
		results[0]++;
		if ((*comparator)(data + mid*e_size, data + lo*e_size) >= 0) {
			results[0]++;
			if ((*comparator)(data + mid*e_size, data + hi*e_size) <= 0 ) return mid;
			else return hi;
		} else return lo;
	} else {
		results[0]++;
		if ((*comparator)(data + mid*e_size, data + hi*e_size) >= 0) {
			results[0]++;
			if ((*comparator)(data + mid*e_size, data + lo*e_size) <= 0) return mid;
			else return lo;
		} else return hi;
	}
}

static int partition(void* data, int lo, int hi, int e_size, int (*comparator)(void*, void*), int* results) {
	int i = lo;
	
	void* pivot = data + e_size*median(data, lo, hi, e_size, comparator, results);

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


static void qs(void* data, int lo, int hi, int e_size, int (*comparator)(void*, void*), int* results) {
	if (lo < hi) {
		if (hi - lo < 16) {
			int* res = insertion_sort(data + lo*e_size, e_size, hi - lo + 1, comparator);
			results[0] += res[0];
			results[1] += res[1];
			free(res);
		} else {
			int p = partition(data, lo, hi, e_size, comparator, results);
			qs(data, lo, p - 1, e_size, comparator, results);
			qs(data, p + 1, hi, e_size, comparator, results);
		}
	}
}

int* mquick_sort(void* data, int e_size, int size, int (*comparator)(void*, void*)) {
	int* results = malloc(2*sizeof(int));
	results[0] = 0;
	results[1] = 0;
	
	qs(data, 0, size - 1, e_size, comparator, results);
	
	return results;
}