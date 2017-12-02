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
	private int i = 0;
	private static int DELAY = 100;
	
	@Override
	public void execute(Entity entity) {
//		if (!animationName.equals(prevAnimationName)) {
//			FileDataManager manager = new FileDataManager(FileDataManager.FileDataFolders.IMAGES);
//			List<InputStream> images = manager.retrieveSubfolderFiles(animationName);
//			for (int i = 0; i < images.size(); i++) {
//				myAnimation.add(new Image(images.get(i)));
//			}
//			prevAnimationName = animationName;
//		}
		//may need to add timer
		if(i % DELAY == 0) {
			int delay = (i / DELAY);
			entity.getRender().setImage(myAnimation.get(delay % myAnimation.size()));
			entity.getRender().displayUpdate(entity.getTransform());
		}
		i++

	}

	@Override
	public void start() {
		myAnimation = new ArrayList<>();
	}
}