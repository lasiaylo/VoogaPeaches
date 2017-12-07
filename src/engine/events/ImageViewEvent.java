package engine.events;

import javafx.scene.image.Image;

public class ImageViewEvent extends Event {
    private Image myImage;

    public ImageViewEvent(Image image) {
        super(EventType.IMAGE_VIEW.getType());
        myImage = image;
    }

    public Image getImage() {
        return myImage;
    }

}
