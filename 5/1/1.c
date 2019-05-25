#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "queue.h"

#define clz(x) __builtin_clz(x)

static inline int ilog2(int x) {
	return sizeof(int) * CHAR_BIT - clz(x) - 1;
}

static inline int numberOfSetBits(int i) {
	i = i - ((i >> 1) & 0x55555555);
  i = (i & 0x33333333) + ((i >> 2) & 0x33333333);
  return (((i + (i >> 4)) & 0x0F0F0F0F) * 0x01010101) >> 24;
}

int** generate(int k) {
	int** t = malloc((1<<k)*sizeof(int*));
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

int bfs(int** graph, int size, int start, int end, int* parent) {
	int visited[1 << size];
	memset(visited, 0, sizeof(visited));
	Queue q = {NULL, NULL, 0};
	qPush(&q, start);
	visited[start] = 1;
	parent[start] = -1;

	while (q.size) {
		int u = qPop(&q);
		for (int v = 0; v < size; v++) {
			if (visited[u | (1 << v)] == 0 && graph[u][v] > 0) {
				qPush(&q, u | (1 << v));
				parent[u | (1 << v)] = u;
				visited[u | (1 << v)] = 1;
			}
		}
	}
	
	return visited[end] == 1;
}

int fordFulkerson(int** graph, int size, int start, int end) {
	int** rGraph = malloc((1 << size)*sizeof(int*));
	for (int i = 0; i < (1 << size); i++) {
		rGraph[i] = malloc(size * sizeof(int));
		for (int j = 0; j < size; j++) {
			rGraph[i][j] = graph[i][j];
		}
	}

	int parent[1 << size];
	int maxFlow = 0;

	while (bfs(rGraph, size, start, end, parent)) {
		int pathFlow = INT_MAX;
		for (int v = end; v != start; v = parent[v]) {
			int u = parent[v];
			pathFlow = pathFlow > rGraph[u][ilog2(u^v)] ? rGraph[u][ilog2(u^v)] : pathFlow;
		}

		for (int v = end; v != start; v = parent[v]) {
			int u = parent[v];
			rGraph[u][ilog2(u^v)] -= pathFlow;
			rGraph[v][ilog2(u^v)] += pathFlow;
		}
		maxFlow += pathFlow;
	}

	return maxFlow;
} 

int main(void) {
	int SIZE = 3;
	srand(time(NULL));
	int** graph = generate(SIZE);
	for (int i = 0; i < 1 << SIZE; i++) {
		for (int j = 0; j < SIZE; j++) printf("%10d ", graph[i][j]);
		printf("\n");
	}
	printf("ff: %d\n", fordFulkerson(graph, SIZE, 0, (1 << SIZE) - 1));
	for (int i = 0; i < 1 << SIZE; i++) free(graph[i]);
	free(graph);
	return 0;
}