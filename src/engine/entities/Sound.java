package engine.entities;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * @author richardtseng
 *
 */
public class Sound {

	private MediaPlayer myMediaPlayer;
	
	/**
	 * Creates a new Sound object for database
	 */
	private Sound() {
	}
	
	/**
	 * @param sound sets MediaPlayer to the passed Media object
	 */
	public void setMediaPlayer(Media sound) {
		myMediaPlayer = new MediaPlayer(sound);
	}
	
	/**
	 * @return MediaPlayer object
	 */
	public MediaPlayer getMediaPlayer() {
		return myMediaPlayer;
	}
}