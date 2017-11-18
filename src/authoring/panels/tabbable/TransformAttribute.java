package authoring.panels.tabbable;

import java.util.ResourceBundle;

import engine.entities.Entity;
import engine.entities.Render;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class TransformAttribute {
	private ResourceBundle myResources = ResourceBundle.getBundle("transform");
	private Render myRender;
	private GridPane myGrid;
	private TitledPane myPane;
	
	public TransformAttribute(Entity entity) {
		myRender = entity.getRender();
		myGrid = new GridPane();
		myPane = new TitledPane();
		myGrid.add(new Rectangle(100,100), 0, 0);
		myPane.setContent(myGrid);
	}

	public Node getNode() {
		return myPane;
	}
	
	
}
