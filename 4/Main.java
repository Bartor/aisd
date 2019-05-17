import tree.impl.BST;
import tree.impl.RBT;

public class Main {
    public static void main(String[] args) {
        RBT<String> tree = new RBT<>();

        tree.insert("a");
        tree.insert("z");
        tree.insert("e");
        tree.insert("f");
        tree.insert("a");
        tree.insert("z");
        tree.insert("i");
        tree.insert("a");
        System.out.println(tree.inOrder());
        tree.delete("a");
        tree.delete("z");
        System.out.println(tree.inOrder());
    }
}
