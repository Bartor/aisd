package tree;

import java.io.File;

public interface TreeInterface<T extends Comparable<T>> {
    public void insert(T element);
    public void delete(T element);
    public boolean search(T element);
    public void load(File file);
    public String inOrder();
}
