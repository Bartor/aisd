package tree.impl;

import tree.TreeInterface;
import tree.structs.Color;
import tree.structs.ColoredNode;
import tree.structs.Stats;

public class RBT<T extends Comparable<T>> extends StringLoadingTree<T> implements TreeInterface<T> {
    private ColoredNode<T> guard = new ColoredNode<>(null);
    private ColoredNode<T> root = guard;

    private Stats stats;

    public RBT(Stats stats) {
        this.stats = stats;
    }

    @Override
    public void insert(T element) {
        ColoredNode<T> z = new ColoredNode<>(element);
        ColoredNode<T> y = guard;
        ColoredNode<T> x = root;

        while (x != guard) {
            stats.nodeComp++;
            stats.nodeChange++;
            y = x;
            stats.keyComp++;
            x = z.compareTo(x) < 0 ? x.getL() : x.getR();
        }

        stats.nodeChange++;
        z.setP(y);
        stats.nodeComp++;
        if (y == guard) {
            stats.nodeChange++;
            root = z;
        } else if (z.compareTo(y) < 0) {
            stats.nodeChange++;
            stats.nodeComp++;
            y.setL(z);
        } else {
            stats.nodeChange++;
            stats.nodeComp++;
            y.setR(z);
        }
        stats.nodeChange += 2;
        z.setL(guard);
        z.setR(guard);
        z.setColor(Color.R);
        insertFix(z);
    }

    @Override
    public void delete(T element) {
        ColoredNode<T> z = lookUp(root, element);
        stats.nodeComp++;
        if (z != null) {
            ColoredNode<T> y = z;
            Color yc = y.getColor();
            ColoredNode<T> x;
            stats.nodeComp++;
            if (z.getL() == guard) {
                x = z.getR();
                trans(z, z.getR());
            } else if (z.getR() == guard) {
                stats.nodeComp++;
                x = z.getL();
                trans(z, z.getL());
            } else {
                stats.nodeComp++;
                y = min(z.getR());
                yc = y.getColor();
                x = y.getR();
                stats.nodeComp++;
                if (y.getP() == z) {
                    stats.nodeChange++;
                    x.setP(y);
                } else {
                    trans(y, y.getR());
                    stats.nodeChange += 2;
                    y.setR(z.getR());
                    y.getR().setP(y);
                }
                trans(z, y);
                stats.nodeChange += 2;
                y.setL(z.getL());
                y.getL().setP(y);
                y.setColor(z.getColor());
            }
            if (yc == Color.B) deleteFix(x);
        }
    }

    @Override
    public boolean search(T element) {
        return lookUp(root, element) != guard;
    }

    @Override
    public String inOrder() {
        StringBuilder b = new StringBuilder();

        inOrderBuilder(root, b);

        return b.toString();
    }

    private void inOrderBuilder(ColoredNode<T> current, StringBuilder b) {
        if (current != guard) {
            inOrderBuilder(current.getL(), b);
            b.append(current);
            b.append("\n");
            inOrderBuilder(current.getR(), b);
        }
    }

    private void insertFix(ColoredNode<T> z) {
        while (z.getP().getColor() == Color.R) {
            stats.nodeComp++;
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

    private void deleteFix(ColoredNode<T> x) {
        while(x != root && x.getColor() == Color.B) {
            stats.nodeComp += 2;
            if (x == x.getP().getL()) {
                ColoredNode<T> w = x.getP().getR();
                if (w.getColor() == Color.R) {
                    w.setColor(Color.B);
                    x.getP().setColor(Color.R);
                    lr(x.getP());
                    w = x.getP().getR();
                }
                if (w.getL().getColor() == Color.B && w.getR().getColor() == Color.B) {
                    w.setColor(Color.R);
                    x = x.getP();
                } else {
                    if (w.getR().getColor() == Color.B) {
                        w.getL().setColor(Color.B);
                        w.setColor(Color.R);
                        rr(w);
                        w = x.getP().getR();
                    }
                    w.setColor(x.getP().getColor());
                    x.getP().setColor(Color.B);
                    w.getR().setColor(Color.B);
                    lr(x.getP());
                    x = root;
                }
            } else {
                ColoredNode<T> w = x.getP().getL();
                if (w.getColor() == Color.R) {
                    w.setColor(Color.B);
                    x.getP().setColor(Color.R);
                    rr(x.getP());
                    w = x.getP().getL();
                }
                if (w.getR().getColor() == Color.B && w.getL().getColor() == Color.B) {
                    w.setColor(Color.R);
                    x = x.getP();
                } else {
                    if (w.getL().getColor() == Color.B) {
                        w.getR().setColor(Color.B);
                        w.setColor(Color.R);
                        lr(w);
                        w = x.getP().getL();
                    }
                    w.setColor(x.getP().getColor());
                    x.getP().setColor(Color.B);
                    w.getL().setColor(Color.B);
                    rr(x.getP());
                    x = root;
                }
            }
        }
        x.setColor(Color.B);
    }

    private void rr(ColoredNode<T> y) {
        ColoredNode<T> x = y.getL();
        stats.nodeChange++;
        y.setL(x.getR());
        stats.nodeComp++;
        if (y.getL() != guard) {
            stats.nodeChange++;
            x.getR().setP(y);
        }
        stats.nodeChange++;
        x.setP(y.getP());
        stats.nodeComp++;
        if (y.getP() == guard) {
            stats.nodeChange++;
            root = x;
        } else if (y == y.getP().getR()) {
            stats.nodeComp++;
            stats.nodeChange++;
            y.getP().setR(x);
        } else {
            stats.nodeComp++;
            stats.nodeChange++;
            y.getP().setL(x);
        }
        stats.nodeChange += 2;
        x.setR(y);
        y.setP(x);
    }

    private void lr(ColoredNode<T> x) {
        ColoredNode<T> y = x.getR();
        stats.nodeChange++;
        x.setR(y.getL());
        stats.nodeComp++;
        if (y.getL() != guard) {
            stats.nodeChange++;
            y.getL().setP(x);
        }
        stats.nodeChange++;
        y.setP(x.getP());
        stats.nodeComp++;
        if (x.getP() == guard) {
            stats.nodeChange++;
            this.root = y;
        } else if (x == x.getP().getL()) {
            stats.nodeComp++;
            stats.nodeChange++;
            x.getP().setL(y);
        } else {
            stats.nodeComp++;
            stats.nodeChange++;
            x.getP().setR(y);
        }
        stats.nodeChange += 2;
        y.setL(x);
        x.setP(y);
    }

    private ColoredNode<T> lookUp(ColoredNode<T> x, T k) {
        while (x != guard && k.compareTo(x.getKey()) != 0) {
            stats.nodeComp++;
            stats.keyComp += 2;
            if (k.compareTo(x.getKey()) < 0) {
                x = x.getL();
            } else {
                x = x.getR();
            }
        }
        return x;
    }

    private void trans(ColoredNode<T> u, ColoredNode<T> v) {
        stats.nodeComp++;
        if (u.getP() == guard) {
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
        stats.nodeChange++;
        v.setP(u.getP());
    }

    private ColoredNode<T> min(ColoredNode<T> x) {
        while(x.getL() != guard) {
            stats.nodeComp++;
            x = x.getL();
        }
        return x;
    }

    @Override
    public String toString() {
        return "RBT";
    }
}
