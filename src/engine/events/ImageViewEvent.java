package engine.events;

import javafx.scene.image.Image;

public class ImageViewEvent extends Event {
    private Image image;


    public ImageViewEvent(Image image) {
        super(EventType.IMAGE_VIEW.getType());
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

}
