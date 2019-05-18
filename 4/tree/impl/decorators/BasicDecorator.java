package tree.impl.decorators;

public class BasicDecorator implements StringDecoratorInterface {
    @Override
    public String decorate(String s) {
        return s.replaceAll("^[^a-zA-Z]*", "");
    }
}
