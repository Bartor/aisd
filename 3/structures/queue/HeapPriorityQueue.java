package structures.queue;

import java.util.ArrayList;

public class HeapPriorityQueue implements PriorityQueueInterface {
    private ArrayList<QueueElementInterface> elements = new ArrayList<>();

    @Override
    public void insert(QueueElementInterface element) {
        elements.add(element);
        siftUp(elements.size() -1);
    }

    @Override
    public void priority(QueueElementInterface element) {
        //todo implement this in O(logn) somehow
        for (int i = 0; i < elements.size(); i++) {
            QueueElementInterface e = elements.get(i);
            if (e.getValue().equals(element.getValue()) && element.getPriority() > e.getPriority()) {
                e.setPriority(e.getPriority());
                siftUp(i);
            }
        }
    }

    @Override
    public boolean empty() {
        return elements.isEmpty();
    }

    @Override
    public QueueElementInterface top() {
        if (elements.isEmpty()) return null;
        else return elements.get(0);
    }

    @Override
    public QueueElementInterface pop() {
        if (elements.isEmpty()) return null;
        else {
            QueueElementInterface returnValue = elements.get(0);
            swap(0, elements.size() - 1);
            elements.remove(elements.size() - 1);
            siftDown(0);
            return returnValue;
        }
    }

    public String print() {
        StringBuilder b = new StringBuilder();
        for (QueueElementInterface e : elements) {
            b.append("(");
            b.append(e.getValue());
            b.append(", ");
            b.append(e.getPriority());
            b.append("), ");
        }
        return b.toString();
    }

    private void siftDown(int index) {
        int current, left, right;
        int size = elements.size();
        while (index < size) {
            current = index;

            left = 2 * current + 1;
            right = left + 1;

            if (left < size && elements.get(left).getPriority() > elements.get(current).getPriority()) {
                current = left;
            }
            if (right < size && elements.get(right).getPriority() > elements.get(current).getPriority()) {
                current = right;
            }

            if (current == index) return;

            swap(current, index);

            index = current;
        }
    }

    private void siftUp(int index) {
        int current, parent;
        while (index >= 0) {
            current = index;

            parent = (current - 1)/2;

            if (parent >= 0 && elements.get(parent).getPriority() > elements.get(current).getPriority()) {
                current = parent;
            }

            if (current == index) return;

            swap(current, index);

            index = current;
        }
    }

    private void swap(int first, int second) {
        QueueElementInterface temp = elements.get(first);
        elements.set(first, this.elements.get(second));
        elements.set(second, temp);
    }
}
