#ifndef queue
#define queue

typedef struct Item {
    int value;
    struct Item* next;
} Item;

typedef struct {
    Item* front;
    Item* rear;
    int size;
} Queue;

void qPush(Queue* q, int e);

int qPop(Queue* q);

int qFront(Queue* q);

int qBack(Queue* q);

#endif