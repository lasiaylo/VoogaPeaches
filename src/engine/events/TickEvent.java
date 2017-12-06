package engine.events;

public class TickEvent extends Event{
    private double dt;
    public TickEvent(double dt) {
        super("tick");
        this.dt = dt;
    }

    public double getDt() {
        return dt;
    }
}
