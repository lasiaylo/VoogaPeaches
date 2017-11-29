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
	private String grass = "resources/graphics/Background/grass.png";

	File filename;

	FileInputStream myInputStream;

	@Override
	public void execute(Entity entity) {
        FileDataManager manager = new FileDataManager(FileDataManager.FileDataFolders.IMAGES);
		Image myImage = new Image(manager.readFileData("Background/grass.png"));
		entity.getRender().setImage(myImage);

		entity.getRender().displayUpdate(entity.getTransform());
	}

	@Override
	public void start() {
		//filename = new File(grass);
	}
}