package engine.events;

/**
 * Specifies that an Evented needs to accelerate
 * @author Albert
 */
public class AccelerateEvent extends Event {
    private double dvx;
    private double dvy;

    /**
     * Creates a new Accelerate event
     * @param dvx   x acceleration
     * @param dvy   y acceleration
     */
    public AccelerateEvent(double dvx, double dvy) {
        super(EventType.ACCELERATE.getType());
        this.dvx = dvx;
        this.dvy = dvy;
    }

    /**
     * @return  x acceleration
     */
    public double dvx() {
        return dvx;
    }

    /**
     * @return  y acceleration
     */
    public double dvy() {
        return dvy;
    }
}
