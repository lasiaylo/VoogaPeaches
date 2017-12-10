package authoring.panels.attributes;

public class ObjectSetter implements Setter {

    private Object myObject;

    public ObjectSetter(Object object){
        myObject = object;
    }
    @Override
    public Object getValue() {
        return myObject;
    }

    @Override
    public void setValue(Object arg) {
        myObject = arg;
    }
}