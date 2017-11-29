package engine.entities;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * @author richardtseng
 *
 */
public class Sound {

	private Map<String, MediaPlayer> mediaMap;
	private MediaPlayer myMediaPlayer;
	
	public Sound() {
		mediaMap = new HashMap<>();
	}

	/**
	 * @return map of MediaPlayer
	 */
	public Map<String, MediaPlayer> getMediaPlayerMap() {
		return mediaMap;
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