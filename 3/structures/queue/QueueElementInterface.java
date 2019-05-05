package structures.queue;

public interface QueueElementInterface<T extends Comparable<T>> {
    public T getValue();
    public void setPriority(int priority);
    public int getPriority();
}