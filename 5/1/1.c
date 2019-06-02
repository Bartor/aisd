#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <limits.h>

#include "queue.h"
#include "pcg_basic.h"

#define clz(x) __builtin_clz(x)

#define SIZE 16
#define REPS 100

pcg32_random_t rng;

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
			t[i][j] = !((1<<j) & i) ? ((int) pcg32_random_r(&rng)) % limit + 1 : 0;
			if (t[i][j] < 0) t[i][j] = -t[i][j];
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

void glpkPrint(int** graph, int size) {
	//this is retarded but i don't care at this point
	printf("param n, integer, >= 2;\n\nset V, default {1..n};\n\nset E, within V cross V;\n\nparam a{(i,j) in E}, > 0;\n\nparam s, symbolic, in V, default 1;\n\nparam t, symbolic, in V, != s, default n;\n\nvar x{(i,j) in E}, >= 0, <= a[i,j];\n\nvar flow, >= 0;\n\ns.t. node{i in V}:\n\n   sum{(j,i) in E} x[j,i] + (if i = s then flow)\n\n   =\n\n   sum{(i,j) in E} x[i,j] + (if i = t then flow);\n\nmaximize obj: flow;\n\nsolve;\n\nprintf{1..56} \"=\"; printf \"\\n\";\nprintf \"Maximum flow from node %%s to node %%s is %%g\\n\\n\", s, t, flow;\n\ndata;\n");
	printf("param n := %d;\n\n", 1 << size);
	printf("param : E : a :=\n");
	for (int i = 0; i < 1 << size; i++) {
		for (int j = 0; j < size; j++) {
			//glpk indexes from 1 for whatever reason
			if (graph[i][j] > 0) printf("\t%d %d %d\n", 1 + i, 1 + (i | (1 << j)), graph[i][j]);
		}
	}
	printf(";\nend;\n");
}

int main(int argc, char** argv) {
	pcg32_srandom_r(&rng, time(NULL), 42u);

	if (argc > 1) {
		int** graph = generate(atoi(argv[1]));
		glpkPrint(graph, atoi(argv[1]));
		return 0;
	}

	printf("i, flow, time, paths\n");
	for (int i = 1; i <= SIZE; i++) {
		printf("%3d, ", i);
		float mFlow = 0, mTime = 0, mPaths = 0;
		for (int j = 0; j < REPS; j++) {
			int** graph = generate(i);

			int paths = 0;
			clock_t s = clock();
			mFlow += 1.0*fordFulkerson(graph, i, 0, (1 << i) - 1, &paths)/REPS;
			mTime += 1.0*(clock() - s)/(CLOCKS_PER_SEC*REPS);
			mPaths += 1.0*paths/REPS;

			for (int k = 0; k < 1 << i; k++) free(graph[k]);
			free(graph);
		}
		printf("%20f, %20f, %20f\n", mFlow, mTime, mPaths);
	}
	return 0;
}