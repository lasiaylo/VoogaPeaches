package engine.events;

public class GamingEvent extends Event {

    private boolean isGaming;

    public GamingEvent(boolean isGaming) {
        super(EventType.GAMING.getType());
        this.isGaming = isGaming;
    }

    public boolean getIsGaming() {
        return this.isGaming;
    }

}
