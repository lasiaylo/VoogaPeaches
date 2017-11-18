package engine.entities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import engine.managers.EntityManager;

/**
 * control the game loop of engine, start or stop the game
 * 
 * might need to be moved to engine root package
 * 
 * @author estellehe
 *
 */
public class EngineLoop {
	private static final int MAX_FRAMES_PER_SECOND = 60;
	
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
		myManager.updateAll();
	}
	
	/**
	 * get the timeline
	 */
	public Timeline getTimeline() {

	    return myTimeline;
	}

}
