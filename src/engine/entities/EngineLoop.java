package engine.entities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * control the game loop of engine, start or stop the game
 * 
 * might need to be moved to engine root package
 * 
 * @author estellehe
 *
 */
public class EngineLoop {
	private static final int MAX_FRAMES_PER_SECOND = 100;
	
	private EntityManager myManager;
	private Timeline myTimeline;
	
	/**
	 * constructor for game loop
	 * @param manager
	 */
	public EngineLoop(EntityManager manager) {
		myManager = manager;
		KeyFrame frame = new KeyFrame(Duration.millis(1000 / MAX_FRAMES_PER_SECOND), e -> step());
		myTimeline = new Timeline();
		myTimeline.setCycleCount(Timeline.INDEFINITE);
		myTimeline.getKeyFrames().add(frame);
	}
	
	private void step() {
		//assume we do not make any changes to the background block entity from engine
		for (Entity each: myManager.getBGEntity()) {
			each.update();
			// condition should be set here to displayupdate only for those entities that are in the range of camera
			// like based on how far away from player since player is always centered
			getTransform();
			each.getRender()
				.displayUpdate(each.getTransform());
		}
	}
	
	/**
	 * pause the game loop
	 */
	public Timeline getTimeline() {
		return myTimeline;
	}

}
