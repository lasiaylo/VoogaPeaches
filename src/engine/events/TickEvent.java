package engine.events;

public class TickEvent extends Event{
    private double dt;
    public TickEvent(double dt) {
        super(EventType.TICK.getType());
        this.dt = dt;
    }

    public double getDt() {
        return dt;
    }
}
