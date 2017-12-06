package engine.events;

public class MoveEvent extends Event {
    private double dx;
    private double dy;

    public MoveEvent(double dx, double dy) {
        super("move");
        this.dx = dx;
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }
}
