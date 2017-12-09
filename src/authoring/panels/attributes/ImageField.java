package authoring.panels.attributes;

import database.filehelpers.FileConverter;
import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class ImageField extends Field {

    public static final int IMAGE_HEIGHT = 200;
    public static final int IMAGE_WIDTH = 200;
    public static final String USER_IMAGES = "user_images/";

    private ImageView view;
    private File selectedImage;
    private FileChooser fileChooser;
    private FileDataManager manager;
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
        initializeFileChooser();
        String file = (String) getValue();
        manager = new FileDataManager(FileDataFolders.IMAGES);
        Image image = new Image(manager.readFileData(file));
        view = new ImageView(image);
        view.setFitHeight(IMAGE_HEIGHT);
        view.setFitWidth(IMAGE_WIDTH);
        setControl(view);
    }

    @Override
    protected void setControlAction() {
        view.setOnMouseClicked(e -> chooseFile());
    }

    private void initializeFileChooser(){
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter JPG_PNG_GIF = new FileChooser.ExtensionFilter("JPG files (*.jpg), PNG files (*.png), GIF files (*.gif)", "*.jpg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().addAll(JPG_PNG_GIF);
    }

    private void chooseFile() {
        File selectedImage = fileChooser.showOpenDialog(null);
        if (selectedImage != null){
            setValue(selectedImage.getName());
            Image image = new Image(selectedImage.toURI().toString());
            manager.writeFileData(FileConverter.convertImageToByteArray(image),USER_IMAGES + selectedImage.getName());
            view.setImage(image);
        }
    }

    @Override
    protected void getDefaultValue() {
        //do nothing, default value is already set in makeControl
    }
}