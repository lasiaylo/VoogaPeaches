package authoring.panels.tabbable;

import authoring.Panel;
import authoring.PanelController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import util.Loader;
import voogasalad.util.sound.Sound;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BGMPanel implements Panel {

    private final static String SOUNDS_PATH = "resources/sounds/";
    private final static String[] SUPPORTED_EXTENSIONS = new String[]{"wav", "mp3"};
    public static final String CHOOSE_MUSIC_FILE = "Choose current file: ";
    public static final String NONE = "None";

    private HBox box;
    private ObjectProperty<Sound> current;

    public BGMPanel(){
        current = new SimpleObjectProperty<>();
        Label label = new Label(CHOOSE_MUSIC_FILE);
        ChoiceBox<String> choices = new ChoiceBox<>();
        choices.getItems().add(NONE);
        ObservableMap<String, Sound> music = FXCollections.observableHashMap();
        for(String extension : SUPPORTED_EXTENSIONS){
            try {
                List<String> files = Arrays.asList(Loader.validFiles(SOUNDS_PATH, extension));
                choices.getItems().addAll(files);
                for (String e : files) {
                    music.put(e, new Sound(SOUNDS_PATH + e + "." + extension));
                }
            } catch (FileNotFoundException e) {}
        }
        choices.valueProperty().addListener((observable, oldValue, newValue) -> current.setValue(music.get(newValue)));
        box = new HBox(label, choices);
    }

    @Override
    public Region getRegion() {
        return box;
    }

    @Override
    public void setController(PanelController controller) {
        controller.setMusicProperty(current);
    }

    @Override
    public String title() {
        return "Background Music";
    }
}
