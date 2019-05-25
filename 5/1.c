#include <time.h>
#include <stdlib.h>
#include <stdio.h>

static inline int numberOfSetBits(int i) {
	i = i - ((i >> 1) & 0x55555555);
  i = (i & 0x33333333) + ((i >> 2) & 0x33333333);
  return (((i + (i >> 4)) & 0x0F0F0F0F) * 0x01010101) >> 24;
}

int** generate(int k) {
	int** t = malloc((1<<k)*k*sizeof(int*));
	for (int i = 0; i < (1<<k); i++) {
		t[i] = malloc(k * sizeof(int));
		int ones = numberOfSetBits(i);
		int limit = (ones + 1 > k - ones ? 1 << (ones + 1) : 1 << (k - ones));
		for (int j = 0; j < k; j++) {
			t[i][j] = !((1<<j) & i) ? rand() % limit + 1 : 0;
		}
	}
	return t;
}

int main(void) {
	int SIZE = 8;
	srand(time(NULL));
	int** graph = generate(SIZE);
	for (int i = 0; i < 1 << SIZE; i++) {
		for (int j = 0; j < SIZE; j++) printf("%10d ", graph[i][j]);
		printf("\n");
	}
	for (int i = 0; i < 1 << SIZE; i++) free(graph[i]);
	free(graph);
	return 0;
}