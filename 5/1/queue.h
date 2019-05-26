#ifndef lqueue
#define lqueue

typedef struct Item {
    int value;
    struct Item* next;
} Item;

typedef struct {
    int rear;
    int front;
    int size;
    int* array;
    int overflow;
    int elements;
} Queue;

Queue qNew(int size);

void qPush(Queue* q, int e);

int qPop(Queue* q);

int qFront(Queue* q);

int qBack(Queue* q);

void qDelete(Queue* q);

#endif