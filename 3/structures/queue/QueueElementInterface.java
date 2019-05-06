package structures.queue;

public interface QueueElementInterface<T extends Comparable<T>, K extends Comparable<K>> {
    public T getValue();
    public void setPriority(K priority);
    public K getPriority();
}