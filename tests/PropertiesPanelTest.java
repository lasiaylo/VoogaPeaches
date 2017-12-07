import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.json.JSONObject;

import authoring.panels.attributes.Attribute;
import authoring.panels.tabbable.AttributesPanel;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import engine.entities.Entity;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PropertiesPanelTest extends Application{
	private AttributesPanel panel;
	private Entity testEntity;

	@Override
	public void start(Stage arg0) throws Exception {
		panel = new AttributesPanel();
		panel.updateProperties((testEntity = createEntity()));
		
		setupStage();
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {

		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	private void setupStage() {
		Scene scene = new Scene(panel.getRegion());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

	private Entity createEntity() {

		JSONDataManager manager = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT);
        JSONObject blueprint = manager.readJSONFile("test");
        JSONToObjectConverter<Entity> converter = new JSONToObjectConverter<>(Entity.class);

        Entity entity = converter.createObjectFromJSON(Entity.class,blueprint);
		System.out.println(entity.getProperties().toString());
		return entity;
	}

	public static void main(String[] args) {
		launch();
	}
	
}
