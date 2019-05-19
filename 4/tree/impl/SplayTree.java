package tree.impl;

import tree.TreeInterface;
import tree.structs.Node;
import tree.structs.Stats;

import java.util.Stack;

public class SplayTree<T extends Comparable<T>> extends StringLoadingTree<T> implements TreeInterface<T> {
    private Node<T> root;
    private Stats stats;

    public SplayTree(Stats stats) {
        this.stats = stats;
    }

    @Override
    public void insert(T element) {
        Node<T> e = bstInsert(element);

        stats.nodeComp++;
        if (e != null) {
            stats.nodeComp++;
            if (e.getP() != null) {
                Node<T> p = e.getP();
                while (p.getP() != null) {
                    stats.nodeComp++;
                    splay(p);
                }
            }
        }
    }

    @Override
    public void delete(T element) {
        Node<T> e = bstDelete(element);
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
        while (x != null || s.size() > 0) {
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
        Node<T> parent = n.getP();
        stats.nodeComp++;
        Node<T> gp = (parent != null) ? parent.getP() : null;
        stats.nodeComp++;
        if (parent != null) {
            stats.nodeComp++;
            if (parent == root) {
                root = n;
                n.setP(null);

                stats.nodeComp++;
                if (n == parent.getL()) {
                    parent.setL(n.getR());
                    stats.nodeComp++;
                    if (n.getR() != null) n.getR().setP(parent);
                    stats.nodeChange += 2;

                    n.setR(parent);
                    parent.setP(n);
                } else {
                    parent.setR(n.getL());
                    stats.nodeComp++;
                    if (n.getL() != null) n.getL().setP(parent);
                    stats.nodeChange += 2;

                    n.setL(parent);
                    parent.setP(n);
                }
                return;
            }
        }

        stats.nodeComp++;
        if (parent != null) {
            stats.nodeComp++;
            if (gp != null) {
                Node<T> ggp = gp.getP();
                stats.nodeComp += 2;
                if (ggp != null && ggp.getL() == gp) {
                    stats.nodeChange += 2;

                    ggp.setL(n);
                    n.setP(ggp);
                } else if (ggp != null && ggp.getR() == gp) {
                    stats.nodeChange += 2;

                    stats.nodeComp += 2;
                    ggp.setR(n);
                    n.setP(ggp);
                } else {
                    stats.nodeComp += 2;
                    root = n;
                    n.setP(null);
                }

                stats.nodeComp += 3;
                if ((n == parent.getL() && parent == gp.getL()) || (n == parent.getR() && parent == gp.getR())) {
                    stats.nodeComp++;
                    if (n == parent.getL()) {
                        Node<T> nr = n.getR();
                        stats.nodeChange += 2;
                        n.setR(parent);
                        parent.setP(n);

                        parent.setL(nr);
                        stats.nodeComp++;
                        if (nr != null) nr.setP(parent);

                        Node<T> pr = parent.getR();
                        parent.setR(gp);
                        gp.setP(parent);

                        gp.setL(pr);
                        stats.nodeComp++;
                        if (pr != null) pr.setP(gp);
                    } else {
                        Node<T> nl = n.getL();
                        stats.nodeChange += 2;
                        n.setL(parent);
                        parent.setP(n);

                        parent.setR(nl);
                        stats.nodeComp++;
                        if (nl != null) nl.setP(parent);

                        Node<T> pl = parent.getL();
                        parent.setL(gp);
                        gp.setP(parent);

                        gp.setR(pl);
                        stats.nodeComp++;
                        if (pl != null) pl.setP(gp);
                    }
                    return;
                }

                stats.nodeComp++;
                if (n == parent.getL()) {
                    Node<T> nl = n.getR();
                    Node<T> nr = n.getL();

                    stats.nodeChange += 4;
                    n.setR(parent);
                    parent.setP(n);

                    n.setL(gp);
                    gp.setP(n);

                    parent.setL(nl);
                    stats.nodeComp++;
                    if (nl != null) nl.setP(parent);

                    gp.setR(nr);
                    stats.nodeComp++;
                    if (nr != null) nr.setP(gp);

                    return;
                }

                Node<T> nl = n.getL();
                Node<T> nr = n.getR();

                stats.nodeChange += 4;
                n.setL(parent);
                parent.setP(n);

                n.setR(gp);
                gp.setP(n);

                stats.nodeChange += 4;
                parent.setR(nl);
                stats.nodeComp++;
                if (nl != null) nl.setP(parent);

                gp.setL(nr);
                stats.nodeComp++;
                if (nr != null) nr.setP(gp);
            }
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
        while (e != null && value.compareTo(e.getKey()) != 0) {
            stats.nodeComp += 2;
            stats.keyComp++;
            if (value.compareTo(e.getKey()) < 0) {
                e = e.getL();
            } else {
                e = e.getR();
            }
        }
        return e;
    }

    private Node<T> bstInsert(T element) {
        Node<T> y = null;
        Node<T> x = root;

        Node<T> z = new Node<>(element);

        while (x != null) {
            stats.nodeComp++;
            y = x;
            if (z.compareTo(x) < 0) {
                x = x.getL();
            } else {
                x = x.getR();
            }
        }

        stats.nodeChange++;
        z.setP(y);

        stats.nodeComp++;
        if (y == null) {
            stats.nodeChange++;
            root = z;
        } else if (z.compareTo(y) < 0) {
            stats.nodeComp++;
            stats.nodeChange++;
            y.setL(z);
        } else {
            stats.nodeComp++;
            stats.nodeChange++;
            y.setR(z);
        }

        return z;
    }

    private Node<T> bstDelete(T element) {
        Node<T> z = bstLookUp(root, element);

        if (z != null) {
            stats.nodeComp++;
            if (z.getL() == null) {
                return trans(z, z.getR());
            } else if (z.getR() == null) {
                stats.nodeComp++;
                return trans(z, z.getL());
            } else {
                stats.nodeComp++;
                Node<T> y = min(z.getR());
                stats.nodeComp++;
                if (y.getP() != z) {
                    trans(y, y.getR());
                    stats.nodeChange += 2;
                    y.setR(z.getR());
                    y.getR().setP(y);
                }
                trans(z, y);
                stats.nodeChange += 2;
                y.setL(z.getL());
                y.getL().setP(y);
                return y;
            }
        }
        return null;
    }

    private Node<T> trans(Node<T> u, Node<T> v) {
        stats.nodeComp++;
        if (u.getP() == null) {
            stats.nodeChange++;
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

        return v;
    }

    private Node<T> min(Node<T> x) {
        while (x.getL() != null) {
            x = x.getL();
        }
        return x;
    }

    @Override
    public String toString() {
        return "SPLAY";
    }

    public Stats getStats() {
        return stats;
    }
}
