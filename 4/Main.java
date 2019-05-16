import tree.impl.BST;

public class Main {
    public static void main(String[] args) {
        BST<String> tree = new BST<>();

        tree.insert("a");
        tree.insert("z");
        tree.insert("e");
        tree.insert("f");
        tree.insert("a");
        tree.insert("z");
        tree.insert("i");
        tree.insert("a");
        System.out.println(tree.inOrder());
    }
}
