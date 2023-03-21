package demo;

public class SimpleContainer implements Container {

    private Object contained = null;

    @Override
    public void clear() {
        contained = null;
    }

    @Override
    public boolean putItem(Object o) {
        contained = o;
        return true;
    }

    @Override
    public Object getItem() {
        return contained;
    }
}
