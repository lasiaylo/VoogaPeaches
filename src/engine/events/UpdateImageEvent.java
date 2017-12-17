package engine.events;

import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Event that specifies updating imageview
 * @author estellehe
 */
public class UpdateImageEvent extends Event {

    private ImageView view;

    /**
     * Creates a new UpdateImageEvent
     * @param path  path of new image
     */
    public UpdateImageEvent(String path) {
        super(EventType.UPDATE_IMAGE.getType());
        view = new ImageView(new Image(new FileDataManager(FileDataFolders.IMAGES).readFileData(path)));
    }

    /**
     * @return  updated imageview
     */
    public ImageView getView() {
        return view;
    }

}
