import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.json.JSONObject;

import authoring.panels.attributes.Attribute;
import authoring.panels.tabbable.PropertiesPanel;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import engine.entities.Entity;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PropertiesPanelTest extends Application{
	private PropertiesPanel panel;
	private Entity testEntity;

	@Override
	public void start(Stage arg0) throws Exception {
		panel = new PropertiesPanel();
		testEntity = createEntity();
		panel.updateProperties(testEntity);
		
		setupStage();
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
		return entity;
	}

	public static void main(String[] args) {
		launch();
	}
	
}
