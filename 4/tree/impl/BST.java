package tree.impl;

import tree.TreeInterface;
import tree.structs.Node;
import tree.structs.Stats;

import java.util.Stack;

public class BST<T extends Comparable<T>> extends StringLoadingTree<T> implements TreeInterface<T> {
    private Node<T> root;
    private Stats stats;

    public BST(Stats stats) {
        this.stats = stats;
        root = null;
    }

    @Override
    public void insert(T element) {
        Node<T> e = new Node<>(element);

        Node<T> y = null;
        Node<T> x = root;

        while (x != null) {
            stats.nodeComp++;
            stats.nodeChange++;
            y = x;
            stats.keyComp++;
            x = e.compareTo(x) < 0 ? x.getL() : x.getR();
        }

        e.setP(y);
        stats.nodeChange++;
        if (y == null) {
            stats.nodeComp++;
            root = e;
        } else if (e.compareTo(y) < 0) {
            stats.nodeComp += 2;
            stats.nodeChange++;
            y.setL(e);
        } else {
            stats.nodeComp += 2;
            stats.nodeChange++;
            y.setR(e);
        }
    }

    @Override
    public void delete(T element) {
        Node<T> z = lookUpNode(root, element);
        stats.nodeComp++;
        if (z != null) {
            if (z.getL() == null) {
                stats.keyComp++;
                trans(z, z.getR());
            } else if (z.getR() == null) {
                stats.keyComp += 2;
                trans(z, z.getL());
            } else {
                stats.keyComp += 2;
                Node<T> y = min(z.getR());
                stats.nodeComp++;
                if (y.getP() != z) {
                    trans(y, y.getR());
                    stats.nodeChange+=2;
                    y.setR(z.getR());
                    y.getR().setP(y);
                }
                trans(z, y);
                stats.nodeChange+=2;
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
            stats.nodeComp++;
            while (x != null) {
                stats.nodeComp++;
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
        while (e != null && value.compareTo(e.getKey()) != 0) {
            stats.nodeComp++;
            stats.keyComp += 2;
            if (value.compareTo(e.getKey()) < 0) {
                e = e.getL();
            } else {
                e = e.getR();
            }
        }
        return e;
    }

    private void trans(Node<T> u, Node<T> v) {
        stats.nodeComp++;
        if (u.getP() == null) {
            root = v;
        } else if (u == u.getP().getL()) {
            stats.nodeComp++;
            stats.nodeChange++;
            u.getP().setL(v);
        } else {
            stats.nodeComp++;
            stats.nodeChange++;
            u.getP().setR(v);
        }
        stats.nodeComp++;
        if (v != null) {
            stats.nodeChange++;
            v.setP(u.getP());
        }
    }

    private Node<T> min(Node<T> x) {
        while (x.getL() != null) {
            stats.nodeComp++;
            x = x.getL();
        }
        return x;
    }

    @Override
    public String toString() {
        return "BST";
    }

    public Stats getStats() {
        return stats;
    }
}
