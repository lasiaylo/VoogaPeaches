package engine.scripts.defaults

import database.filehelpers.FileDataManager
import engine.entities.Entity
import engine.scripts.defaults.GroovyScript
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer

/**
 * @author Richard Tseng
 *
 */
class SoundScript extends GroovyScript{

	String soundName;
	private String prevSoundName;
	
	@Override
	public void execute(Entity entity) {
		if (!soundName.equals(prevSoundName)){
			FileDataManager manager = new FileDataManager(FileDataManager.FileDataFolders.SOUNDS);
			Media sound = new Media(manager.readFileData(soundName));
			entity.getSound().setMediaPlayer(sound);
			prevSoundName = soundName;
		}
		entity.getSound().getMediaPlayer().play();
	}

	@Override
	public void start() {	
	}
}