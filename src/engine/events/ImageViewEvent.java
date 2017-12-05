package engine.events;

import javafx.scene.image.Image;

public class ImageViewEvent extends Event {
    private Image image;


    public ImageViewEvent(Image image) {
        super("Image View Event");
    }

    public Image getImage() {
        return image;
    }

}
