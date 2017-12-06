package engine.events;

public enum EventType {
    CLICK(ClickEvent.class),

    IMAGE_VIEW(ImageViewEvent.class),

    KEY_PRESS(KeyPressEvent.class),
    MAP(AddLayerEvent.class),
    MOVE(MoveEvent.class),
    STATE(StateEvent.class),
    TICK(TickEvent.class);

    private Class clazz;

    EventType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
