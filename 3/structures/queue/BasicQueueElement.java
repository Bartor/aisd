package structures.queue;

public class BasicQueueElement<T extends Comparable<T>, K extends Comparable<K>> implements QueueElementInterface<T, K> {
    private T value;
    private K priority;

    public BasicQueueElement(T value, K priority) {
        this.value = value;
        this.priority = priority;
    }

    @Override
    public K getPriority() {
        return priority;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setPriority(K priority) {
        this.priority = priority;
    }
}
