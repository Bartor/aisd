#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "pcg_basic.h"

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
}