
import authoring.panels.tabbable.AttributesPanel;
import engine.entities.Entity;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class PropertiesPanelTester extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		AttributesPanel panel = new AttributesPanel();
		panel.updateProperties(createEntity());
		Region region = panel.getRegion();

		Scene scene = new Scene(region);
		stage.setTitle("tesssstttt");
		stage.setScene(scene);
		stage.show();
	}
	
	private Entity createEntity() {
		Entity entity = new Entity(null, 10,10);
		return entity;
	}

	public static void main(String[] args) {
		launch();
	}
	
}
