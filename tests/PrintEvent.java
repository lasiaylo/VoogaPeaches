import engine.events.Event;

public class PrintEvent extends Event {
    private String message;
    public PrintEvent(String message) {
        super("print");
    }

    public void printHellYeah() {
        System.out.println("hell yeah");
    }
}
