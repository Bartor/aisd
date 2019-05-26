#include <stdlib.h>

#include "queue.h"

Queue qNew(int size) {
    int* arr = malloc(size*sizeof(int));
    Queue q = {
        -1,
        0,
        size,
        arr,
        0,
        0
    };
    return q;
}

void qPush(Queue* q, int e) {
    if (q->elements == q->size) {
        perror("overflow");
        exit(1);
    }
    q->elements++;
    if (q->rear == q->size - 1) {
        q->rear = 0;
        q->overflow = 1;
        q->array[q->rear] = e;
    } else {
        q->rear++;
        q->array[q->rear] = e;
    }
}

int qPop(Queue* q) {
    if (!q->elements) {
        perror("empty pop");
        exit(1);
    }
    q->elements--;
    if (q->front == q->size - 1) {
        if (q->rear != q->front) {
            perror("something went wrong");
            exit(1);
        }
        q->overflow = 0;
        q->front = 0;
        q->rear = 0;
        return q->array[q->size - 1];
    } else {
        return q->array[q->front++];
    }
}

int qFront(Queue* q) {
    if (q->front == -1) return -1;
    return q->array[q->front];
}

int qBack(Queue* q) {
    if (q->front == -1) return -1;
    return q->array[q->rear];
}

void qDelete(Queue* q) {
    free(q->array);
}