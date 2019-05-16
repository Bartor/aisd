package tree.impl;

import tree.TreeInterface;
import tree.structs.Color;
import tree.structs.ColoredNode;

public class RBT<T extends Comparable<T>> extends StringLoadingTree<T> implements TreeInterface<T> {
    private ColoredNode<T> guard = new ColoredNode<>(null);
    private ColoredNode<T> root = guard;

    @Override
    public void insert(T element) {
        ColoredNode<T> z = new ColoredNode<>(element);
        ColoredNode<T> y = guard;
        ColoredNode<T> x = root;

        while (x != guard) {
            y = x;
            x = z.compareTo(x) < 0 ? x.getL() : x.getR();
        }

        z.setP(y);
        if (y == guard) {
            root = z;
        } else if (z.compareTo(y) < 0) {
            y.setL(z);
        } else {
            y.setR(z);
        }
        z.setL(guard);
        z.setR(guard);
        z.setColor(Color.R);
        insertFix(z);
    }

    @Override
    public void delete(T element) {

    }

    @Override
    public boolean search(T element) {
        return false;
    }

    @Override
    public String inOrder() {
        return null;
    }

    private void insertFix(ColoredNode<T> z) {
        while (z.getP().getColor() == Color.R) {
            if (z.getP() == z.getP().getP().getL()) {
                ColoredNode<T> y = z.getP().getP().getR();
                if (y.getColor() == Color.R) {
                    z.getP().setColor(Color.B);
                    y.setColor(Color.B);
                    z.getP().getP().setColor(Color.R);
                    z = z.getP().getP();
                } else {
                    if (z == z.getP().getR()) {
                        z = z.getP();
                        lr(z);
                    }
                    z.getP().setColor(Color.B);
                    z.getP().getP().setColor(Color.R);
                    rr(z.getP().getP());
                }
            } else {
                ColoredNode<T> y = z.getP().getP().getL();
                if (y.getColor() == Color.R) {
                    z.getP().setColor(Color.B);
                    y.setColor(Color.B);
                    z.getP().getP().setColor(Color.R);
                    z = z.getP().getP();
                } else {
                    if (z == z.getP().getL()) {
                        z = z.getP();
                        rr(z);
                    }
                    z.getP().setColor(Color.B);
                    z.getP().getP().setColor(Color.R);
                    lr(z.getP().getP());
                }
            }
        }
        root.setColor(Color.B);
    }

    private void rr(ColoredNode<T> y) {

    }

    private void lr(ColoredNode<T> x) {

    }
}
