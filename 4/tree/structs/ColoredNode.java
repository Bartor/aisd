package tree.structs;

public class ColoredNode<T extends Comparable<T>> implements Comparable<ColoredNode<T>> {
    private Color color;
    private T key;
    private ColoredNode<T> l;
    private ColoredNode<T> r;
    private ColoredNode<T> p;

    public ColoredNode(T key) {
        this.key = key;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ColoredNode<T> getL() {
        return l;
    }

    public ColoredNode<T> getP() {
        return p;
    }

    public ColoredNode<T> getR() {
        return r;
    }

    public T getKey() {
        return key;
    }

    public void setR(ColoredNode<T> r) {
        this.r = r;
    }

    public void setP(ColoredNode<T> p) {
        this.p = p;
    }

    public void setL(ColoredNode<T> l) {
        this.l = l;
    }

    @Override
    public int compareTo(ColoredNode<T> o) {
        return this.key.compareTo(o.getKey());
    }

    @Override
    public String toString() {
        return (color == Color.R ? "R" : "B") + " " + key;
    }
}
