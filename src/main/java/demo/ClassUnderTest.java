package demo;

public class ClassUnderTest {

    private Container container;

    public ClassUnderTest() {
        container = new SimpleContainer();
        container.putItem(0);
    }

    public Object returnValue() {
        return container.getItem();
    }

}
