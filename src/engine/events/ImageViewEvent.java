package engine.events;

import javafx.scene.image.ImageView;

public class ImageViewEvent extends Event {
    private ImageView myImageView;
    private String action;

    public ImageViewEvent(String type) {
        super(type);
    }

    public void setMouseTransparent(boolean trans) {
        myImageView.setMouseTransparent(trans);
    }

    public void setVisible(boolean vis) {
        myImageView.setVisible(vis);
    }
    public void setView(ImageView view) {
        myImageView = view;
    }
}
