package engine.events;

/**
 * Event that specifies the tick of an engine
 * @author Albert
 */
public class TickEvent extends Event{
    private double dt;

    /**
     * Creates a new TickEvent
     * @param dt    time of one tick
     */
    public TickEvent(double dt) {
        super(EventType.TICK.getType());
        this.dt = dt;
    }

    /**
     * @return  time of one tick
     */
    public double getDt() {
        return dt;
    }
}
