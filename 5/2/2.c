#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "pcg_basic.h"

#define START 3
#define SIZE 10

#define REPS 100

pcg32_random_t rng;

int exists(int* hay, int needle, int size) {
    while (size-- > 0) {
        if (hay[size] == needle) return 1;
    }
    return 0;
}

int** generate(int size, int neighbours) {
    int** g = malloc((1 << size) * sizeof(int*));
    for (int i = 0; i < 1 << size; i++) {
        g[i] = malloc(neighbours * sizeof(int));
        for (int j = 0; j < neighbours; j++) {
            int neighbour = ((int) pcg32_random_r(&rng)) % (1 << size);
            if (neighbour < 0) neighbour = -neighbour;
            while (exists(g[i], neighbour, j)) {
                neighbour = ((int) pcg32_random_r(&rng)) % (1 << size);
                if (neighbour < 0) neighbour = -neighbour;
            }
            g[i][j] = neighbour;
        }
    }
    return g;
}

int bpm(int** graph, int size, int neighbours, int start, int* seen, int* match) {
    for (int v = 0; v < size; v++) {
        if (exists(graph[start], v, neighbours) && !seen[v]) {
            seen[v] = 1;
            if (match[v] < 0 || bpm(graph, size, neighbours, match[v], seen, match)) {
                match[v] = start;
                return 1;
            }
        }
    }
    return 0;
}

void glpkPrint(int** graph, int size, int neighbours) {
    //this is retarded but i don't care at this point
    printf("param n, integer, >= 2;\n\nset V, default {1..n};\n\nset E, within V cross V;\n\nparam a{(i,j) in E}, > 0;\n\nparam s, symbolic, in V, default 1;\n\nparam t, symbolic, in V, != s, default n;\n\nvar x{(i,j) in E}, >= 0, <= a[i,j];\n\nvar flow, >= 0;\n\ns.t. node{i in V}:\n\n   sum{(j,i) in E} x[j,i] + (if i = s then flow)\n\n   =\n\n   sum{(i,j) in E} x[i,j] + (if i = t then flow);\n\nmaximize obj: flow;\n\nsolve;\n\nprintf{1..56} \"=\"; printf \"\\n\";\nprintf \"Maximum flow from node %%s to node %%s is %%g\\n\\n\", s, t, flow;\n\ndata;\n");
	printf("param n := %d;\n\n", (1 << (size+1)) + 2);
	printf("param : E : a :=\n");
    for (int i = 0; i < 1 << size; i++) {
        printf("\t1 %d 1\n", i+2);
    }
	for (int i = 0; i < 1 << size; i++) {
		for (int j = 0; j < neighbours; j++) {
			printf("\t%d %d 1\n", i+2, (1 << size) + graph[i][j] + 2);
		}
	}
    for (int i = 0; i < 1 << size; i++) {
        printf("\t%d %d 1\n", (1 << size) + i + 2, (1 << (size+1)) + 2);
    }
	printf(";\nend;\n");
}


int maxMatching(int** graph, int size, int neighbours) {
    int match[size];
    memset(match, -1, sizeof(match));

    int res = 0;
    for (int i = 0; i < size; i++) {
        int seen[size];
        memset(seen, 0, sizeof(seen));

        if (bpm(graph, size, neighbours, i, seen, match)) res++;
    }
    return res;
}

int main(int argc, char** argv) {
    pcg32_srandom_r(&rng, time(NULL), 42u);

    if (argc > 2) {
        int** graph = generate(atoi(argv[1]), atoi(argv[2]));
        glpkPrint(graph, atoi(argv[1]), atoi(argv[2]));
        return 0;
    }
    
    printf(",");
    for (int i = 1; i <= SIZE; i++) printf("%d,%d,", i, i);
    printf("\n");
    for (int i = START; i <= SIZE; i++) {
        printf("%d,", i);
        for (int j = 1; j <= i; j++) {
            float matching = 0, time = 0;
            for (int r = 0; r < REPS; r++) {
                int** g = generate(i, j);

                clock_t t = clock();
                matching += 1.0*maxMatching(g, 1 << i, j)/REPS;
                time += 1.0*(clock() - t)/(CLOCKS_PER_SEC*REPS);

                for (int f = 0; f < 1 << i; f++) free(g[f]);
                free(g);
            }
            printf("%f,%f,", matching, time);
        }
        printf("\n");
    }
}