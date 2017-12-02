
import authoring.panels.tabbable.AttributesPanel;
import engine.entities.Entity;
import engine.scripts.IScript;
import engine.scripts.Script;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import util.exceptions.GroovyInstantiationException;

import java.util.ArrayList;
import java.util.List;

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
	
	private Entity createEntity() throws GroovyInstantiationException {
		List<IScript> bruh = new ArrayList<IScript>();
		Entity entity = new Entity(bruh, 145.0,10.0);
		Script script = new Script("defaults/AttributeTestScript.groovy");
		entity.getScripts().add(script);
		return entity;
	}

	public static void main(String[] args) {
		try {
		launch();
		} catch(RuntimeException e) {
		}
	}
	
}
