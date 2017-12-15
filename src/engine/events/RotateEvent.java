package engine.events;

public class RotateEvent extends Event {
    private double angle;
    public RotateEvent(double angle) {
        super(EventType.RESET.getType());
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }
}
