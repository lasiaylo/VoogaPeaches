package engine.events;

import javafx.scene.image.Image;

public class ImageViewEvent extends Event {
    private Image myImage;


    public ImageViewEvent(Image image) {
        super("Image View Event");
        myImage = image;
    }

    public Image getImage() {
        return myImage;
    }

}
