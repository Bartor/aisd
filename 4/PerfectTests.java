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
    public static void main(String[] args) {
        File bstf = new File("gen/pbst.txt");

        TreeInterface[] trees = new TreeInterface[3];
        File[] files = {bstf};

        for (File f : files) {
            trees[2] = new RBT<String>(new Stats());
            trees[1] = new SplayTree<String>(new Stats());
            trees[0] = new BST<String>(new Stats());

            for (TreeInterface t : trees) {
                System.out.println(f + " : " + t);

                DecoratedWordLoader d = new DecoratedWordLoader(new BasicDecorator());
                List<String> words = d.load(bstf);

                long time = System.nanoTime();
                for (String word : words) {
                    t.insert(word);
                }
                System.out.println("insert done in " + (System.nanoTime() - time));
                time = System.nanoTime();
                for (String word : words) {
                    t.search(word);
                }
                System.out.println("search done in " + (System.nanoTime() - time));
                time = System.nanoTime();
                for (String word : words) {
                    t.delete(word);
                }
                System.out.println("delete done in " + (System.nanoTime() - time));
            }
        }
    }
}
