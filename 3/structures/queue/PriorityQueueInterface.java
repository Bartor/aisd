package structures.queue;

public interface PriorityQueueInterface {
    public void insert(QueueElementInterface element);
    public void priority(QueueElementInterface element);
    public boolean empty();
    public QueueElementInterface top();
    public QueueElementInterface pop();
}