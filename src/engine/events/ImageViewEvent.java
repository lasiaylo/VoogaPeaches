package engine.events;

/**
 * Event that represents a changing of image
 * @author estellehe
 */
public class ImageViewEvent extends Event {

    private String myPath;

    /**
     * Creates a new ImageViewEvent
     * @param path  path of new image
     */
    public ImageViewEvent(String path) {
        super(EventType.IMAGE_VIEW.getType());
        myPath = path;
    }

    /**
     * @return path to change to
     */
    public String getPath() {
        return myPath;
    }

}
