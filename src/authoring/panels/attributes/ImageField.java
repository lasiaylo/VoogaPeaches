package authoring.panels.attributes;

import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

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
        String file = (String) getValue();
        FileDataManager manager = new FileDataManager(FileDataFolders.IMAGES);
        Image image = new Image(manager.readFileData(file));
        view = new ImageView(image);
        setControl(view);
    }

    @Override
    protected void setControlAction() {
        view.setOnMouseClicked(e -> chooseFile());
    }

    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter JPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter PNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        FileChooser.ExtensionFilter GIF = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
        fileChooser.getExtensionFilters().addAll(JPG, PNG, GIF);
        fileChooser.showOpenDialog(null);

    }

    @Override
    protected void getDefaultValue() {

    }
}
