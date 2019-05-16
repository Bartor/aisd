package tree.impl;

import tree.TreeInterface;

import java.io.*;
import java.util.Scanner;

public abstract class StringLoadingTree<T extends Comparable<T>> implements TreeInterface<T> {
    @Override
    public void load(File file) {
        Scanner s;
        try {
            s = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        while (s.hasNextLine()) {
            for (String word: s.nextLine().split(" ")) {
                insert((T) word);
            }
        }
    }
}
