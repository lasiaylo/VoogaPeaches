package engine.entities;

import engine.camera.Camera;
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
	private Timeline myGamingTimeline;
	private Timeline myEngineTimeline;
	private Camera myCamera;
	
	/**
	 * constructor for game loop
	 * @param manager
	 */
	public EngineLoop(EntityManager manager, Camera camera) {
		myManager = manager;
		myCamera = camera;
		KeyFrame gameFrame = new KeyFrame(Duration.millis(1000 / MAX_FRAMES_PER_SECOND), e -> myManager.updateAll());
		KeyFrame engineFrame = new KeyFrame(Duration.millis(1000 / MAX_FRAMES_PER_SECOND), e -> myCamera.update());


		myGamingTimeline = new Timeline();
		myGamingTimeline.setCycleCount(Timeline.INDEFINITE);
		myGamingTimeline.getKeyFrames().add(gameFrame);

		myEngineTimeline = new Timeline();
		myEngineTimeline.setCycleCount(Timeline.INDEFINITE);
		myEngineTimeline.getKeyFrames().add(engineFrame);
	}

	
	/**
	 * get the timeline
	 */
	public Timeline getTimeline() {

	    return myGamingTimeline;
	}

}
