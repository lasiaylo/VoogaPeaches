package engine.scripts.defaults

import engine.entities.Entity
import javafx.scene.image.Image
import javafx.scene.image.ImageView

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
		entity.getRender().setImage(new ImageView(myImage));
		entity.getRender().displayUpdate(entity.getTransform());
	}

	@Override
	public void start() {
		filename = new File();
	}
}