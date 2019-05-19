package tree;

import tree.structs.Stats;

import java.io.File;
import java.util.List;

public interface TreeInterface<T extends Comparable<T>> {
    public void insert(T element);
    public void delete(T element);
    public boolean search(T element);
    public List<String> load(File file);
    public String inOrder();
    public Stats getStats();
}
