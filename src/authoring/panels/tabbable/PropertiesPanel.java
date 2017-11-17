package authoring.panels.tabbable;

import authoring.Panel;
import authoring.ScreenPosition;
import javafx.scene.layout.Region;

public class PropertiesPanel implements Panel {

	@Override
	public Region getRegion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScreenPosition getPosition() {
		return ScreenPosition.TOP_RIGHT;
	}

	@Override
	public String title() {
		return "Properties";
	}

}
