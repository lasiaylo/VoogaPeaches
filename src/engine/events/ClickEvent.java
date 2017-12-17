package engine.events;

import javafx.scene.input.MouseEvent;

/**
 * An event that specifies a click on the event
 * @author Albert
 * @author estellehe
 */
public class ClickEvent extends Event {

    private MouseEvent myMouseEvent;
    private boolean isGaming = true;

    /**
     * Creates a new ClickEvent
     */
    private ClickEvent() {
        super(EventType.CLICK.getType());
    }

    /**
     * Creates a new ClickEvent
     * @param mouse Mouse Event that represents click
     */
    public ClickEvent(MouseEvent mouse) {
        this();
        myMouseEvent = mouse;
    }

    /**
     * Creates a new ClickEvent
     * @param gaming    whether or not the engine is gaming
     * @param event     Mouse Event that represents click
     */
    public ClickEvent(boolean gaming, MouseEvent event) {
        super(EventType.CLICK.getType());
        isGaming = gaming;
        myMouseEvent = event;
    }

    /**
     * @return  mouse event related to click
     */
    public MouseEvent getMouseEvent() {
        return myMouseEvent;
    }

    /**
     * @return  whether or not engine is gaming
     */
    public boolean getIsGaming() {
        return isGaming;
    }


}
