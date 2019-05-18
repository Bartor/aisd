import tree.TreeInterface;
import tree.impl.BST;
import tree.impl.RBT;
import tree.impl.SplayTree;
import tree.impl.decorators.BasicDecorator;
import tree.structs.Stats;

import java.io.File;
import java.util.List;

public class Main {
    private static final int testNumber = 1;

    public static void main(String[] args) {
        Stats rbts = new Stats();
        Stats bsts = new Stats();
        Stats spls = new Stats();
        SplayTree<String> spl = new SplayTree<>(spls);
        RBT<String> rbt = new RBT<>(rbts);
        BST<String> bst = new BST<>(bsts);
        spl.addDecorator(new BasicDecorator());
        rbt.addDecorator(new BasicDecorator());
        bst.addDecorator(new BasicDecorator());

        TreeInterface[] trees = {rbt, spl, bst};

        File aspell = new File("data/aspell_wordlist.txt");
        File kjb = new File("data/kjb.txt");
        File lotr = new File("data/lotr.txt");
        File sample = new File("data/sample.txt");

        File[] files = {aspell, kjb, lotr, sample};

        for (int i = 0; i < testNumber; i++) {
            System.out.println();
            for (TreeInterface tree : trees) {
                for (File file : files) {
                    System.out.println(tree + ": " + file);
                    System.out.println("total: " + oneTest(file, tree) + " ns");
                }
            }
        }
    }

    private static long oneTest(File file, TreeInterface tree) {
        long t = System.nanoTime();
        long tt = t;
        List<String> words = tree.load(file);
        System.out.println("insert: " + (System.nanoTime() - t) + " ns");
        t = System.nanoTime();
        for (String word : words) {
            tree.search(word);
        }
        System.out.println("search: " + (System.nanoTime() - t) + " ns");
        t = System.nanoTime();
        for (String word : words) {
            tree.delete(word);
        }
        System.out.println("delete: " + (System.nanoTime() - t) + " ns");
        return System.nanoTime() - tt;
    }
}
