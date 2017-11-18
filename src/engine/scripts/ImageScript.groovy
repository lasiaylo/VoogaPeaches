package engine.scripts

import engine.entities.Entity
import javafx.scene.image.Image
import engine.scripts.defaults.GroovyScript


/**
 * @author Richard Tseng
 *
 */
class ImageScript extends GroovyScript{

	File filename;

	@Override
	public void execute(Entity entity) {
		Image myImage = new Image(getClass()
				.getClassLoader()
				.getResourceAsStream(filename.getPath()));
		entity.getRender().setImage(myImage);
	}

	@Override
	public void start() {
		filename = new File();
	}
}