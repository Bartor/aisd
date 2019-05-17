import tree.impl.BST;
import tree.impl.RBT;
import tree.impl.SplayTree;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        RBT<String> tree = new RBT<>();

        tree.load(new File("data/lotr.txt"));
        System.out.println(tree.inOrder());
    }
}
