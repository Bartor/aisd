import com.sun.source.tree.Tree;
import tree.TreeInterface;
import tree.impl.BST;
import tree.impl.RBT;
import tree.impl.SplayTree;
import tree.impl.decorators.BasicDecorator;
import tree.structs.Stats;

import java.io.File;
import java.util.List;

public class PerfectTests {
    private static final int ITERS = 20;

    public static void main(String[] args) {
        File bstf = new File("gen/pbst2.txt");

        TreeInterface[] trees = new TreeInterface[3];
        File[] files = {bstf};

        long[][][] res = new long[files.length][trees.length][3];
        int fc, tc;

        for (int i = 0; i < ITERS; i++)  {
            fc = 0;
            for (File f : files) {
                tc = 0;
                trees[2] = new RBT<String>(new Stats());
                trees[1] = new SplayTree<String>(new Stats());
                trees[0] = new BST<String>(new Stats());

                for (TreeInterface t : trees) {
                    DecoratedWordLoader d = new DecoratedWordLoader(new BasicDecorator());
                    List<String> words = d.load(f);

                    long time = System.nanoTime();
                    for (String word : words) {
                        t.insert(word);
                    }
                    res[fc][tc][0] += (System.nanoTime() - time)/ITERS;
                    time = System.nanoTime();
                    for (String word : words) {
                        t.search(word);
                    }
                    res[fc][tc][1] += (System.nanoTime() - time)/ITERS;
                    time = System.nanoTime();
                    for (String word : words) {
                        t.delete(word);
                    }
                    res[fc][tc][2] += (System.nanoTime() - time)/ITERS;
                    tc++;
                }

                fc++;
            }
        }

        for (long[][] a : res) {
            System.out.println();
            for (long[] b : a) {
                for (long c : b) {
                    System.out.print(c/1000000f + " ");
                }
                System.out.println(" ms");
            }
        }
    }
}
