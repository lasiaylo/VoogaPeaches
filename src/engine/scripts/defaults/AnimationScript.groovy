package engine.scripts.defaults

import database.filehelpers.FileDataManager
import engine.entities.Entity
import engine.scripts.defaults.GroovyScript
import javafx.scene.image.Image
import javafx.scene.image.ImageView

/**
 * @author Richard Tseng
 *
 */
class AnimationScript extends GroovyScript{

	String animationName;
	private String prevAnimationName;
	List<Image> myAnimation;
	
	@Override
	public void execute(Entity entity) {
		if (!animationName.equals(prevAnimationName)) {
			FileDataManager manager = new FileDataManager(FileDataManager.FileDataFolders.IMAGES);
			List<InputStream> images = manager.retrieveSubfolderFiles(animationName);
			for (int i = 0; i < images.size(); i++) {
				myAnimation.add(new Image(manager.readFileData(images.get(i))));
			}
			prevAnimationName = animationName;
		}
		//may need to add timer
		for (int i = 0; i < myAnimation.size(); i++) {
			entity.getRender().setImage(myAnimation.get(i));
			entity.getRender().displayUpdate(entity.getTransform());
		}
	}

	@Override
	public void start() {
		myAnimation = new ArrayList<>();
	}
}