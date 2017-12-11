package engine.events;

public enum EventType {
    ADDLAYER("addLayer"),
    CLICK("click"),
    COLLISION("collision"),
    DRAG_EXITED("drag exited"),
    IMAGE_VIEW("Image View Event"),
    INITIAL_IMAGE("setup initial imageview"),
    KEY_PRESS("key press"),
    MAP("map"),
    MOUSE_DRAG("mouse drag"),
    MOUSE_PRESS("mouse press"),
    MOVE("move"),
    ACCELERATE("accelerate"),
    STATE("state"),
    TICK("tick"),
    TRANSPARENT_MOUSE("Transparent Mouse Event"),
    VIEWVIS("View Visibility Event"),
    MAPSETUP("map setup"),
    UPDATE_IMAGE("update_image");

    private String type;

    EventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
