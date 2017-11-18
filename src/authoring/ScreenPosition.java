package authoring;

public enum ScreenPosition {
    CAMERA(false),
    MENU(false),
    BOTTOM(true),
    TOP_LEFT(true),
    TOP_RIGHT(true),
    BOTTOM_LEFT(true),
    BOTTOM_RIGHT(true);

    private boolean tabbable;

    ScreenPosition(boolean tabbable){
        this.tabbable = tabbable;
    }

    public boolean isTabbed(){
        return tabbable;
    }
}
