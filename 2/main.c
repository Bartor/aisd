#include "sort/selection.h"
#include "sort/quick.h"
#include "sort/mquick.h"
#include "sort/heap.h"
#include "sort/insertion.h"

#include "pcg_basic.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

extern inline int compare_int_desc(void* a, void* b) {
	return *((int*) b) - *((int*) a);
}

extern inline int compare_int_asc(void* a, void* b) {
	return *((int*) a) - *((int*) b);
}

int* random_arr(int size) {
	pcg32_random_t rng;
	pcg32_srandom_r(&rng, time(NULL), 42u);
	
	srand(time(NULL));
	
	int* arr = malloc(size*sizeof(int));
	
	for (int i = 0; i < size; i++) {
		arr[i] = ((int) pcg32_random_r(&rng)) % size;
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
			int *master = random_arr(i);
			int *stat, *arr = malloc(i*sizeof(int));
			double master_stats[5][3] = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}; //hea, ins, qui, sel, mqu | comp, swap, time
			clock_t t;
			
			for (int j = 0; j < times; j++) {
				printf("=RUN %d OF %d=\n", j+1, times);
				
				arr = malloc(i*sizeof(int));
				memcpy(arr, master, i*sizeof(int));
				printf("Heap... ");
				t = clock();
				stat = heap_sort(arr, sizeof(int), i, &compare_int_asc);
				t = clock() - t;
				printf("done, %lf s\n", ((double)t)/CLOCKS_PER_SEC);
				master_stats[0][0] += stat[0]/times;
				master_stats[0][1] += stat[1]/times;
				master_stats[0][2] += ((double)t)/(CLOCKS_PER_SEC*times);
				free(stat);
				free(arr);
				
				arr = malloc(i*sizeof(int));
				memcpy(arr, master, i*sizeof(int));
				printf("Insertion... ");
				t = clock();
				stat = insertion_sort(arr, sizeof(int), i, &compare_int_asc);
				t = clock() - t;
				printf("done, %lf s\n", ((double)t)/CLOCKS_PER_SEC);
				master_stats[1][0] += stat[0]/times;
				master_stats[1][1] += stat[1]/times;
				master_stats[1][2] += ((double)t)/(CLOCKS_PER_SEC*times);
				free(stat);
				free(arr);
				
				arr = malloc(i*sizeof(int));
				memcpy(arr, master, i*sizeof(int));
				printf("Quick... ");
				t = clock();
				stat = quick_sort(arr, sizeof(int), i, &compare_int_asc);
				t = clock() - t;
				printf("done, %lf s\n", ((double)t)/CLOCKS_PER_SEC);
				master_stats[2][0] += stat[0]/times;
				master_stats[2][1] += stat[1]/times;
				master_stats[2][2] += ((double)t)/(CLOCKS_PER_SEC*times);			
				free(stat);
				free(arr);
				
				arr = malloc(i*sizeof(int));
				memcpy(arr, master, i*sizeof(int));
				printf("Selection... ");
				t = clock();
				stat = selection_sort(arr, sizeof(int), i, &compare_int_asc);
				t = clock() - t;
				printf("done, %lf s\n", ((double)t)/CLOCKS_PER_SEC);
				master_stats[3][0] += stat[0]/times;
				master_stats[3][1] += stat[1]/times;
				master_stats[3][2] += ((double)t)/(CLOCKS_PER_SEC*times);			
				free(stat);
				free(arr);
				
				arr = malloc(i*sizeof(int));
				memcpy(arr, master, i*sizeof(int));
				printf("mQuick... ");
				t = clock();
				stat = mquick_sort(arr, sizeof(int), i, &compare_int_asc);
				t = clock() - t;
				printf("done, %lf s\n", ((double)t)/CLOCKS_PER_SEC);
				master_stats[4][0] += stat[0]/times;
				master_stats[4][1] += stat[1]/times;
				master_stats[4][2] += ((double)t)/(CLOCKS_PER_SEC*times);		
				free(stat);
				free(arr);
			}
			fprintf(fd, "%lf,%lf,%lf\n", master_stats[0][0], master_stats[0][1], master_stats[0][2]);
			fprintf(fd, "%lf,%lf,%lf\n", master_stats[1][0], master_stats[1][1], master_stats[1][2]);
			fprintf(fd, "%lf,%lf,%lf\n", master_stats[2][0], master_stats[2][1], master_stats[2][2]);
			fprintf(fd, "%lf,%lf,%lf\n", master_stats[3][0], master_stats[3][1], master_stats[3][2]);
			fprintf(fd, "%lf,%lf,%lf\n", master_stats[4][0], master_stats[4][1], master_stats[4][2]);
			free(master);
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
			case 'm':
				if (asc == 1) res = mquick_sort(arr, sizeof(int), size, &compare_int_asc);
				else res = mquick_sort(arr, sizeof(int), size, &compare_int_desc);
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