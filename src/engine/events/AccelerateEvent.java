package engine.events;

public class AccelerateEvent extends Event {
    private double dvx;
    private double dvy;

    public AccelerateEvent(double dvx, double dvy) {
        super(EventType.ACCELERATE.getType());
        this.dvx = dvx;
        this.dvy = dvy;
    }

    public double dvx() {
        return dvx;
    }

    public double dvy() {
        return dvy;
    }
}
