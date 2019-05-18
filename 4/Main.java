import tree.impl.BST;
import tree.impl.RBT;
import tree.impl.decorators.BasicDecorator;
import tree.structs.Stats;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Stats stats = new Stats();
        BST<String> tree = new BST<>(stats);
        tree.addDecorator(new BasicDecorator());

        long t = System.currentTimeMillis();
        List<String> words = tree.load(new File("data/lotr.txt"));
        for (String word : words) {
            tree.search(word);
        }
        for (String word : words) {
            tree.delete(word);
        }
        System.out.println("==STATS\nKey comparisons: " + stats.keyComp + "\nNode comparisons: " + stats.nodeComp + "\nNode changes: " + stats.nodeChange);
        System.out.println("Time: " + (System.currentTimeMillis() - t) + " ms");
    }
}
