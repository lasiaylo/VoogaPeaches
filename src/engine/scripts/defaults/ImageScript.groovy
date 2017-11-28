package engine.scripts.defaults

import engine.entities.Entity
import javafx.scene.image.Image
import javafx.scene.image.ImageView

/**
 * @author Richard Tseng
 *
 */
class ImageScript extends GroovyScript{
	private String grass = "resources/graphics/Background/grass.png";

	File filename;

	@Override
	public void execute(Entity entity) {
		Image myImage = new Image(new FileInputStream(grass));
		entity.getRender().setImage(myImage);
		entity.getRender().displayUpdate(entity.getTransform());
	}

	@Override
	public void start() {
		//filename = new File(grass);
	}
}