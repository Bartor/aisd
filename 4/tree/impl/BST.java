package tree.impl;

import tree.TreeInterface;
import tree.structs.Node;

import java.util.Stack;

public class BST<T extends Comparable<T>> extends StringLoadingTree<T> implements TreeInterface<T> {
    private Node<T> root;

    public BST() {
        root = null;
    }

    @Override
    public void insert(T element) {
        Node<T> e = new Node<>(element);

        Node<T> y = null;
        Node<T> x = root;

        while (x != null) {
            y = x;
            x = e.compareTo(x) < 0 ? x.getL() : x.getR();
        }

        e.setP(y);
        if (y == null) {
            root = e;
        } else if (e.compareTo(y) < 0) {
            y.setL(e);
        } else {
            y.setR(e);
        }
    }

    @Override
    public void delete(T element) {
        Node<T> z = lookUpNode(root, element);
        if (z != null) {
            if (z.getL() == null) {
                trans(z, z.getR());
            } else if (z.getR() == null) {
                trans(z, z.getL());
            } else {
                Node<T> y = min(z.getR());
                if (y.getP() != z) {
                    trans(y, y.getR());
                    y.setR(z.getR());
                    y.getR().setP(y);
                }
                trans(z, y);
                y.setL(z.getL());
                y.getL().setP(y);
            }
        }
    }

    @Override
    public boolean search(T element) {
        return lookUpNode(root, element) != null;
    }

    @Override
    public String inOrder() {
        Node<T> x = root;
        StringBuilder b = new StringBuilder();

        Stack<Node<T>> s = new Stack<>();
        while(x != null || s.size() > 0) {
            while (x != null) {
                s.push(x);
                x = x.getL();
            }

            x = s.pop();
            b.append(x.getKey());
            b.append("\n");
            x = x.getR();
        }
        return b.toString();
    }

    private Node<T> lookUpNode(Node<T> e, T value) {
        if (e == null || value.compareTo(e.getKey()) == 0) return e;
        if (value.compareTo(e.getKey()) < 0) return lookUpNode(e.getL(), value);
        else return lookUpNode(e.getR(), value);
    }

    private void trans(Node<T> u, Node<T> v) {
        if (u.getP() == null) {
            root = v;
        } else if (u == u.getP().getL()) {
            u.getP().setL(v);
        } else {
            u.getP().setR(v);
        }
        if (v != null) {
            v.setP(u.getP());
        }
    }

    private Node<T> min(Node<T> x) {
        while (x.getL() != null) {
            x = x.getL();
        }
        return x;
    }
}
