#include "queue.h"
#include <stdlib.h>

/* simple implementation of POSITIVE-only integer queue */

void qPush(Queue* q, int e) {
    if (q->size == 0) {
        Item* item = malloc(sizeof(Item));
        item->next = NULL;
        item->value = e;

        q->front = item;
        q->rear = item;
    } else if (q->size == 1) {
        Item* item = malloc(sizeof(Item));
        item->next = NULL;
        item->value = e;

        q->rear = item;
        q->front->next = item;
    } else {
        Item* item = malloc(sizeof(Item));
        item->next = NULL;
        item->value = e;

        q->rear->next = item;
        q->rear = item;
    }
    q->size++;
}

int qPop(Queue* q) {
    if (q->size == 0) return -1;
    else if (q->size == 1) {
        int ret = q->front->value;
        free(q->front);
        q->front = NULL;
        q->size = 0;
        return ret;
    } else {
        Item* front = q->front;
        int ret = front->value;
        q->front = q->front->next;
        free(front);
        q->size--;
        return ret;
    }
}

int qFront(Queue* q) {
    if (q->size == 0) return -1;
    return q->front->value;
}

int qBack(Queue* q) {
    if (q->size == 0) return -1;
    return q->rear->value;
}