#include "sort/selection.h"
#include "sort/quick.h"
#include "sort/heap.h"
#include "sort/insertion.h"

#include "pcg_basic.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

static inline int compare_int_desc(void* a, void* b) {
	return *(int*)b - *(int*)a;
}

static inline int compare_int_asc(void* a, void* b) {
	return *(int*)a - *(int*)b;
}

int* random_arr(int size) {
	pcg32_random_t rng;
	pcg32_srandom_r(&rng, time(NULL), 42u);
	
	int* arr = malloc(size*sizeof(int));
	for (int i = 0; i < size; i++) {
		arr[i] = (int) pcg32_random_r(&rng);
	}
	
	return arr;
}

int main(int argc, char* argv[]) {
	int asc = 0, sta = 0, times = 0;
	char method, file[64];
	
	for (int i = 0; i < argc; i++) {
		if (argv[i][0] == '-' && strlen(argv[i]) > 1) {
			switch(argv[i][1]) {
				case 's':
					sta = 1;
					if (i+1 < argc && argv[i+1][0] != '-') {
						i++;
						strncpy(file, argv[i], 63);
						file[63] = '\0';
					}
					break;
				case 'a':
					asc = 1;
					break;
				case 'd':
					asc = -1;
					break;
				case 't':
					if (i+1 < argc) {
						i++;
						method = argv[i][0];
					}
					break;	
				case 'n':
					if (i+1 < argc) {
						i++;
						times = atoi(argv[i]);
					}
					break;
			}
		}
	}
	
	if (sta) {
		if (strlen(file) == 0) {
			printf("specify filename via -s [filename]\n");
			return 1;
		}
		if(times < 1) {
			printf("specify number of reps via -n [number]\n");
			return 1;
		}
		
		FILE* fd = fopen(file, "w");
		
		for (int i = 100; i <= 10000; i += 100) {
			printf("=====ROUND %d STARTING=====\n", i);
			fprintf(fd, "%d\n", i);
			int* arr;
			int* stat;
			clock_t t;
			
			for (int j = 0; j < times; j++) {
				printf("=RUN %d OF %d=\n", j+1, times);
				
				arr = random_arr(i);
				printf("Heap... ");
				t = clock();
				stat = heap_sort(arr, sizeof(int), i, &compare_int_asc);
				t = clock() - t;
				printf("done, %lf s\n", ((double)t)/CLOCKS_PER_SEC);
				fprintf(fd, "hea,%d,%d,%lf\n", stat[0], stat[1], ((double)t)/CLOCKS_PER_SEC);
				free(stat);
				free(arr);
				
				arr = random_arr(i);
				printf("Insertion... ");
				t = clock();
				stat = insertion_sort(arr, sizeof(int), i, &compare_int_asc);
				t = clock() - t;
				printf("done, %lf s\n", ((double)t)/CLOCKS_PER_SEC);
				fprintf(fd, "ins,%d,%d,%lf\n", stat[0], stat[1], ((double)t)/CLOCKS_PER_SEC);
				free(stat);
				free(arr);
				
				arr = random_arr(i);
				printf("Quick... ");
				t = clock();
				stat = quick_sort(arr, sizeof(int), i, &compare_int_asc);
				t = clock() - t;
				printf("done, %lf s\n", ((double)t)/CLOCKS_PER_SEC);
				fprintf(fd, "qui,%d,%d,%lf\n", stat[0], stat[1], ((double)t)/CLOCKS_PER_SEC);
				free(stat);
				free(arr);
				
				arr = random_arr(i);
				printf("Selection... ");
				t = clock();
				stat = selection_sort(arr, sizeof(int), i, &compare_int_asc);
				t = clock() - t;
				printf("done, %lf s\n", ((double)t)/CLOCKS_PER_SEC);
				fprintf(fd, "sel,%d,%d,%lf\n", stat[0], stat[1], ((double)t)/CLOCKS_PER_SEC);	
				free(stat);
				free(arr);
			}
		}
		
		fclose(fd);
	} else {
		int size;
		scanf("%d", &size);
		
		int* arr = malloc(size*sizeof(int));
		for (int i = 0; i < size; i++) {
			scanf("%d", &arr[i]);
		}
		
		int* res;
		
		switch(method) {
			case 's':
				if (asc == 1) res = selection_sort(arr, sizeof(int), size, &compare_int_asc);
				else res = selection_sort(arr, sizeof(int), size, &compare_int_desc);
				break;
			case 'i':
				if (asc == 1) res = insertion_sort(arr, sizeof(int), size, &compare_int_asc);
				else res = insertion_sort(arr, sizeof(int), size, &compare_int_desc);
				break;
			case 'h':
				if (asc == 1) res = heap_sort(arr, sizeof(int), size, &compare_int_asc);
				else res = heap_sort(arr, sizeof(int), size, &compare_int_desc);
				break;
			case 'q':
				if (asc == 1) res = quick_sort(arr, sizeof(int), size, &compare_int_asc);
				else res = quick_sort(arr, sizeof(int), size, &compare_int_desc);
				break;
			default:
				printf("wrong sorting algorithm\n");
				return 1;
		}
		
		printf("sorted: ");
		for (int i = 0; i < size; i++) {
			printf("%d ", arr[i]);
			if (i+1 < size-1 && ((asc == 1 && arr[i] > arr[i+1]) || (asc == -1 && arr[i] < arr[i+1]))) {
				printf("wrong order!\n");
				return 1;
			}
		}
		printf("\ncomparisons: %d swaps: %d\n", res[0], res[1]);
	}
	
	return 0;
}