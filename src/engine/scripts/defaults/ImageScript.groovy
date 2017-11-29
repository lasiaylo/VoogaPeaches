package engine.scripts.defaults

import database.filehelpers.FileDataManager
import engine.entities.Entity
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage

/**
 * @author Richard Tseng
 *
 */
class ImageScript extends GroovyScript{
		
	String filename;
	private String prevFilename;

	@Override
	public void execute(Entity entity) {
        if (!filename.equals(prevFilename)) {
			FileDataManager manager = new FileDataManager(FileDataManager.FileDataFolders.IMAGES);
			Image myImage = new Image(manager.readFileData(filename));
			entity.getRender().setImage(myImage);
			entity.getRender().displayUpdate(entity.getTransform());
			prevFilename = filename;
		}
	}

	@Override
	public void start() {
	}
}