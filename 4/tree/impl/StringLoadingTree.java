package tree.impl;

import tree.TreeInterface;
import tree.impl.decorators.StringDecoratorInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class StringLoadingTree<T extends Comparable<T>> implements TreeInterface<T> {
    private List<StringDecoratorInterface> decorators = new ArrayList<>();

    public void addDecorator(StringDecoratorInterface decorator) {
        decorators.add(decorator);
    }

    public void removeDecorator(StringDecoratorInterface decorator) {
        decorators.remove(decorator);
    }

    private String applyDecorators(String s) {
        for (StringDecoratorInterface d : decorators) {
            s = d.decorate(s);
        }
        return s;
    }

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
                String insert = applyDecorators(word);
                if (!insert.equals("")) insert((T) insert);
            }
        }
    }
}
