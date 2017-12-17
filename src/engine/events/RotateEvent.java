package engine.events;

/**
 * An event that specifies rotation
 */
public class RotateEvent extends Event {
    private double angle;

    /**
     * Creates a new RotateEvent
     * @param angle angle to set rotation to
     */
    public RotateEvent(double angle) {
        super(EventType.RESET.getType());
        this.angle = angle;
    }

    /**
     * @return  angle to set rotation to
     */
    public double getAngle() {
        return angle;
    }
}
