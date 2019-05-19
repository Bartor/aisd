import tree.TreeInterface;
import tree.impl.BST;
import tree.impl.RBT;
import tree.impl.SplayTree;
import tree.impl.decorators.BasicDecorator;
import tree.impl.decorators.StringDecoratorInterface;
import tree.structs.Stats;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tests {
    private static final int testNumber = 1;

    public static void main(String[] args) {
        TreeInterface[] trees = new TreeInterface[3];

        File aspell = new File("data/aspell_wordlist.txt");
        File kjb = new File("data/kjb.txt");
        File lotr = new File("data/lotr.txt");
        File sample = new File("data/sample.txt");

        File[] files = {aspell, kjb, lotr, sample};

        //for every file, every tree and then insert, search, delete
        long[][][] results = new long[files.length][trees.length][3];
        Stats[][][] stats = new Stats[files.length][trees.length][3];

        for (int i = 0; i < testNumber; i++) {
            System.out.println("\n#### TEST ITERATION " + (i + 1) + " ####\n");
            //we need an iterator index variables to access the results
            for (int j = 0; j < files.length; j++) {
                trees[0] = new RBT<String>(new Stats());
                trees[1] = new SplayTree<String>(new Stats());
                trees[2] = new BST<String>(new Stats());

                System.out.println("### File " + files[j] + " ###\n");

                //load words, cause using in-tree load function is giving some overhead
                DecoratedWordLoader d = new DecoratedWordLoader(new BasicDecorator());
                List<String> wordList = d.load(files[j]);

                for (int k = 0; k < trees.length; k++) {
                    System.out.println("## Tree " + trees[k] + " ##");

                    System.out.print("Insert... ");
                    long t = System.nanoTime();
                    for (String word : wordList) {
                        trees[k].insert(word);
                    }
                    results[j][k][0] += (System.nanoTime() - t) / testNumber;
                    stats[j][k][0] = trees[k].getStats();
                    System.out.println("done in " + (System.nanoTime() - t) + " ns");

                    System.out.print("Search... ");
                    t = System.nanoTime();
                    for (String word : wordList) {
                        trees[k].search(word);
                    }
                    results[j][k][1] += (System.nanoTime() - t) / testNumber;
                    stats[j][k][1] = trees[k].getStats();
                    System.out.println("done in " + (System.nanoTime() - t) + " ns");

                    System.out.print("Delete... ");
                    t = System.nanoTime();
                    for (String word : wordList) {
                        trees[k].delete(word);
                    }
                    results[j][k][2] += (System.nanoTime() - t) / testNumber;
                    stats[j][k][2] = trees[k].getStats();
                    System.out.println("done in " + (System.nanoTime() - t) + " ns\n");
                }
            }
        }

        System.out.println("Time results:\n");

        for (long[][] a : results) {
            for (int i = 0; i < a.length; i++) {
                System.out.print(trees[i] + ",");
                for (long c : a[i]) {
                    System.out.print(c + ",");
                }
                System.out.println();
            }
        }

        System.out.println("Stats changes:\n");
        for (Stats[][] a : stats) {
            for (int i = 0; i < a.length; i++) {
                System.out.print(trees[i] + ",");
                for (int j = 0; j < a[i].length; j++) {
                    System.out.println((j == 0 ? a[i][j].nodeChange : (a[i][j].nodeChange - a[i][j-1].nodeChange)) + ",");
                }
            }
        }

        System.out.println("Stats comparisons:\n");
        for (Stats[][] a : stats) {
            for (int i = 0; i < a.length; i++) {
                System.out.print(trees[i] + ",");
                for (int j = 0; j < a[i].length; j++) {
                    System.out.println((j == 0 ? a[i][j].keyComp + a[i][j].nodeComp : (a[i][j].keyComp + a[i][j].nodeComp - a[i][j-1].nodeComp - a[i][j-1].keyComp)) + ",");
                }
            }
        }

    }
}

class DecoratedWordLoader {
    private StringDecoratorInterface decotrator;

    public DecoratedWordLoader(StringDecoratorInterface decorator) {
        this.decotrator = decorator;
    }

    public List<String> load(File file) {
        List<String> ret = new ArrayList<>();
        Scanner s;
        try {
            s = new Scanner(new FileInputStream(file));
        } catch (Exception e) {
            return ret;
        }
        while (s.hasNextLine()) {
            String[] line = s.nextLine().split(" ");
            for (String word : line) {
                if (!decotrator.decorate(word).equals("")) ret.add(decotrator.decorate(word));
            }
        }

        return ret;
    }
}
