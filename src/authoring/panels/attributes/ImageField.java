package authoring.panels.attributes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageField extends Field {
    ImageView view;

    /**
     * Creates a new Field that needs a way of setting
     *
     * @param setter
     */
    public ImageField(Setter setter) {
        super(setter);
    }

    @Override
    protected void makeControl() {
       Image image = new Image((String) getValue());
       view = new ImageView(image);
       setControl(view);
    }

    @Override
    protected void setControlAction() {
        view.setOnMouseClicked(e->chooseFile());

    }

    private void chooseFile() {
    }

    @Override
    protected void getDefaultValue() {

    }
}
