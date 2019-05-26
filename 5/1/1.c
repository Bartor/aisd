#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>

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
	Queue q = qNew(1 << size);
	qPush(&q, start);
	visited[start] = 1;
	parent[start] = -1;

	while (q.elements) {
		int u = qPop(&q);
		if (u == -1) continue;
		for (int v = 0; v < size; v++) {
			if (visited[u | (1 << v)] == 0 && graph[u][v] > 0) {
				qPush(&q, u | (1 << v));
				parent[u | (1 << v)] = u;
				visited[u | (1 << v)] = 1;
			}
		}
	}

	qDelete(&q);
	
	return visited[end] == 1;
}

int fordFulkerson(int** graph, int size, int start, int end, int* paths) {
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
		*paths = *paths + 1;
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
	int SIZE = 16;
	int REPS = 100;
	srand(time(NULL));
	printf("i, flow, time, paths\n");
	for (int i = 1; i <= SIZE; i++) {
		printf("%3d, ", i);
		float mFlow = 0, mTime = 0, mPaths = 0;
		for (int j = 0; j < REPS; j++) {
			int** graph = generate(i);

			int paths = 0;
			clock_t s = clock();
			mFlow += 1.0*fordFulkerson(graph, i, 0, (1 << i) - 1, &paths)/REPS;
			mTime += (1.0*(clock() - s)/CLOCKS_PER_SEC)/REPS;
			mPaths += 1.0*paths/REPS;

			for (int k = 0; k < 1 << i; k++) free(graph[k]);
			free(graph);
		}
		printf("%20f, %20f, %20f\n", mFlow, mTime, mPaths);
	}
	return 0;
}