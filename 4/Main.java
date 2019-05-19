import tree.TreeInterface;
import tree.impl.BST;
import tree.impl.RBT;
import tree.impl.SplayTree;
import tree.impl.decorators.BasicDecorator;
import tree.structs.Stats;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("type r, b or s as a parameter");
            return;
        }
        TreeInterface tree;

        switch (args[0]) {
            case "s":
                tree = new SplayTree<String>(new Stats());
                break;
            case "r":
                tree = new RBT<String>(new Stats());
                break;
            case "b":
                tree = new BST<String>(new Stats());
                break;
            default:
                System.out.println("choose r, b or a");
                return;
        }

        BasicDecorator d = new BasicDecorator();

        Scanner s = new Scanner(System.in);
        String l;
        while (!(l = s.nextLine()).equals("")) {
            if (l.split(" ").length == 0) continue;
            switch (l.split(" ")[0]) {
                case "i":
                    if (l.split(" ").length > 1) {
                        if (!d.decorate(l.split(" ")[1]).equals("")) {
                            tree.insert(d.decorate(l.split(" ")[1]));
                        }
                    }
                    break;
                case "d":
                    if (l.split(" ").length > 1) {
                        if (!d.decorate(l.split(" ")[1]).equals("")) {
                            tree.delete(d.decorate(l.split(" ")[1]));
                        }
                    }
                    break;
                case "s":
                    if (l.split(" ").length > 1) {
                        if (!d.decorate(l.split(" ")[1]).equals("")) {
                            System.out.println(tree.search(d.decorate(l.split(" ")[1])) ? "1" : "0");
                        }
                    }
                    break;
                case "l":
                    if (l.split(" ").length > 1) {
                        tree.load(new File(l.split(" ")[1]));
                    }
                    break;
                case "p":
                    System.out.println(tree.inOrder());
                    break;
                default:
                    System.out.println("use i, d, s, l or p");
                    break;
            }
        }
    }
}
