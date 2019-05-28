#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "pcg_basic.h"

#define START 3
#define SIZE 10

#define REPS 100

int** generate(int size, int neighbours) {
    pcg32_random_t rng;
	pcg32_srandom_r(&rng, time(NULL), 42u);
    int** g = malloc((1 << size) * sizeof(int*));
    for (int i = 0; i < 1 << size; i++) {
        g[i] = malloc(neighbours * sizeof(int));
        for (int j = 0; j < neighbours; j++) {
            g[i][j] = ((unsigned int) pcg32_random_r(&rng)) % (1 << size);
        }
    }
    return g;
}

int exists(int* hay, int needle, int size) {
    while (size-- > 0) {
        if (hay[size] == needle) return 1;
    }
    return 0;
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

int main(void) {
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