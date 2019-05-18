package tree.impl;

import tree.TreeInterface;
import tree.impl.decorators.StringDecoratorInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    public List<String> load(File file) {
        List<String> ret = new ArrayList<>();

        Scanner s;
        try {
            s = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return ret;
        }
        while (s.hasNextLine()) {
            for (String word : s.nextLine().split(" ")) {
                String insert = applyDecorators(word);
                if (!insert.equals("")) {
                    ret.add(insert);
                    insert((T) insert);
                }
            }
        }
        return ret;
    }
}
