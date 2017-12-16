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
    GAMING("setup gaming"),
    STEP("FSM Stepping"),
    SUBSTITUTE("substitute"),
    RESET("reset"),
    RELEASE("release"),
    ROTATE("rotate"),
    UPDATE_IMAGE("update_image"),
    NOCOLLISION("nocollision");

    private String type;

    EventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
