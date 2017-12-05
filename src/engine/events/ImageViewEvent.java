package engine.events;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageViewEvent extends Event {
    private Image image;
    private String action;

    public ImageViewEvent(Image image) {
        super("Image View Event");
    }

    public Image getImage() {
        return image;
    }

}
