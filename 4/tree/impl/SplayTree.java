package tree.impl;

import tree.TreeInterface;
import tree.structs.Node;

import java.util.Stack;

public class SplayTree<T extends Comparable<T>> extends StringLoadingTree<T> implements TreeInterface<T> {
    private Node<T> root;

    @Override
    public void insert(T element) {
        Node<T> e = bstInsert(element);
        if (e != null) {
            while (e.getP() != null) splay(e);
        }
    }

    @Override
    public void delete(T element) {
        Node<T> e =bstDelete(element);
        if (e != null && e.getP() != null) {
            Node<T> p = e.getP();
            while (p.getP() != null) splay(p);
        }
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

    @Override
    public boolean search(T element) {
        return lookUp(root, element) != null;
    }

    private void splay(Node<T> n) {
        Node<T> p = n.getP();
        Node<T> gp = (p != null) ? p.getP() : null;
        if (p != null && p == root) {
            root = n;
            n.setP(null);

            if (n == p.getL()) {
                p.setL(n.getR());
                if (n.getR() != null) n.getR().setP(p);
                n.setR(p);
                p.setP(n);
            } else {
                p.setR(n.getL());
                if (n.getL() != null) n.getL().setP(p);
                n.setL(p);
                p.setP(n);
            }
            return;
        }
        if (p != null && gp != null) {
            Node<T> ggp = gp.getP();
            if (ggp != null && ggp.getL() == gp) {
                ggp.setL(n);
                n.setP(ggp);
            } else if (ggp != null && ggp.getR() == gp) {
                ggp.setR(n);
                n.setP(ggp);
            } else {
                root = n;
                n.setP(null);
            }

            if ((n == p.getL() && p == gp.getL()) || (n == p.getR() && p == gp.getR())) {
                if (n == p.getL()) {
                    Node<T> nr = n.getR();
                    n.setR(p);
                    p.setP(n);

                    p.setL(nr);
                    if (nr != null) nr.setP(p);

                    Node<T> pr = p.getR();
                    p.setR(gp);
                    gp.setP(p);

                    gp.setL(pr);
                    if (pr != null) pr.setP(gp);
                } else {
                    Node<T> nl = n.getL();
                    n.setL(p);
                    p.setP(n);

                    p.setR(nl);
                    if (nl != null) nl.setP(p);

                    Node<T> pl = p.getL();
                    p.setL(gp);
                    gp.setP(p);

                    gp.setR(pl);
                    if (pl != null) pl.setP(gp);
                }
                return;
            }

            if (n == p.getL()) {
                Node<T> nl = n.getR();
                Node<T> nr = n.getR();

                n.setR(p);
                p.setP(n);

                n.setL(gp);
                gp.setP(n);

                p.setL(nl);
                if (nl != null) nl.setP(p);

                gp.setR(nr);
                if (nr != null) nr.setP(gp);
                return;
            }

            Node<T> nl = n.getL();
            Node<T> nr = n.getR();

            n.setL(p);
            p.setP(n);

            n.setR(gp);
            gp.setP(n);

            p.setR(nl);
            if (nl != null) nl.setP(p);

            gp.setL(nr);
            if (nr != null) nr.setP(gp);
        }
    }

    private Node<T> lookUp(Node<T> x, T k) {
        Node<T> n = bstLookUp(x, k);
        if (n != null) {
            while (n.getP() != null) splay(n);
        }
        return n;
    }

    private Node<T> bstLookUp(Node<T> e, T value) {
        if (e == null || value.compareTo(e.getKey()) == 0) return e;
        if (value.compareTo(e.getKey()) < 0) return bstLookUp(e.getL(), value);
        else return bstLookUp(e.getR(), value);
    }

    private Node<T> bstInsert(T element) {
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

        return e;
    }

    private Node<T> bstDelete(T element) {
        Node<T> z = bstLookUp(root, element);
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
                return y;
            }
        }
        return null;
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
