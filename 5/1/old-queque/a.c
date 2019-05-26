#include "queue.h"
#include <stdio.h>

void pa(int* a, int s) {
    int i = -1;
    while (++i<s) {
        printf("%d ", a[i]);
    }
    printf("\n");
}

int main(void) {
    Queue q = qNew(8);
    qPush(&q, 1);
    qPush(&q, 2);
    qPush(&q, 3);
    qPush(&q, 4);
    qPush(&q, 5);
    qPush(&q, 6);
    qPush(&q, 7);
    qPush(&q, 8);
    pa(q.array, q.size);
    printf("pop:%d\n", qPop(&q));
    pa(q.array, q.size);
    qPush(&q, 9);
    pa(q.array, q.size);
    printf("pop:%d\n", qPop(&q));
    pa(q.array, q.size);
    printf("pop:%d\n", qPop(&q));
    pa(q.array, q.size);
    qPush(&q, 10);
    pa(q.array, q.size);
    qPush(&q, 11);
    pa(q.array, q.size);
    return 0;
}