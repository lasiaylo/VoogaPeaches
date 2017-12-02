import engine.events.Event;

public class PrintEvent extends Event {
    public PrintEvent(String type) {
        super(type);
    }

    public void printHellYeah() {
        System.out.println("hell yeah");
    }
}
