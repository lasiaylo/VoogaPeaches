package engine.events;

import javafx.scene.image.ImageView;

public class ImgViewEvent extends Event{
    private ImageView myImageView;

    public ImgViewEvent(String type) {
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
