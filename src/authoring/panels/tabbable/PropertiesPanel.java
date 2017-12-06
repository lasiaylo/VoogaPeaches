package authoring.panels.tabbable;

import authoring.Panel;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class PropertiesPanel implements Panel {

	private Pane myPane;

	public PropertiesPanel() {
		myPane = new Pane();
		myPane.getStyleClass().add("panel");
	}

	@Override
	public Region getRegion() {
		return myPane;
	}

	@Override
	public String title() {
		return "Properties";
	}

}
