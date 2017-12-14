package engine.events;

public class ImageViewEvent extends Event {

    private String myPath;

    public ImageViewEvent(String path) {
        super(EventType.IMAGE_VIEW.getType());
        myPath = path;
    }

    public String getPath() {
        return myPath;
    }

}
