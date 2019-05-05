package structures.queue;

public class BasicQueueElement<T extends Comparable<T>> implements QueueElementInterface {
    private T value;
    private int priority;

    public BasicQueueElement(T value, int priority) {
        this.value = value;
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
