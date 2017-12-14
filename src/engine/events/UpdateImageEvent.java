package engine.events;

import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UpdateImageEvent extends Event {

    private ImageView view;

    public UpdateImageEvent(String path) {
        super(EventType.UPDATE_IMAGE.getType());
        view = new ImageView(new Image(new FileDataManager(FileDataFolders.IMAGES).readFileData(path)));
    }

    public ImageView getView() {
        return view;
    }

}
