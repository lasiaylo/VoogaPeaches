package engine.events;

public enum EventType {
    ADDLAYER("addLayer"),
    CLICK("click"),
    COLLISION("collision"),
    IMAGE_VIEW("Image View Event"),
    INITIAL_IMAGE(""),
    KEY_PRESS("key press"),
    MAP("map"),
    MOUSE_DRAG("mouse drag"),
    MOUSE_PRESS("mouse press"),
    MOVE("move"),
    STATE("state"),
    TICK("tick"),
    TRANSPARENT_MOUSE("Transparent Mouse Event"),
    VIEWVIS("View Visibility Event");

    private String type;

    EventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
