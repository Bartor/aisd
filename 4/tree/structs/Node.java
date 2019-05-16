package tree.structs;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    private T key;
    private Node<T> l = null;
    private Node<T> r = null;
    private Node<T> p = null;

    public Node(T key) {
        this.key = key;
    }

    public void setL(Node<T> l) {
        this.l = l;
    }

    public void setP(Node<T> p) {
        this.p = p;
    }

    public void setR(Node<T> r) {
        this.r = r;
    }

    public Node<T> getL() {
        return l;
    }

    public Node<T> getP() {
        return p;
    }

    public Node<T> getR() {
        return r;
    }

    public T getKey() {
        return key;
    }

    @Override
    public int compareTo(Node<T> o) {
        return this.key.compareTo(o.getKey());
    }
}
