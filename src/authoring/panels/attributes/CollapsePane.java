package authoring.panels.attributes;

import java.util.Map;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import util.exceptions.GroovyInstantiationException;

public class CollapsePane {
	private final int LABEL_COL = 0;
	private final int ATTRIBUTE_COL = 1;
	private TitledPane myPane;
	private GridPane myGrid;
	
	public CollapsePane(Map<String,?> map, String title) throws GroovyInstantiationException {
		myGrid = new GridPane();
		myPane = new TitledPane(title, myGrid);
		myPane.setAnimated(false);
		addMap(map);
	}
	
	private void addMap(Map<String, ?> map) throws GroovyInstantiationException {
		int row = 0;
		for (String s : map.keySet()) {
			myGrid.add(addLabel(s), LABEL_COL, row);
			myGrid.add(addAttribute(map, s), ATTRIBUTE_COL, row);
			row++;
		}
	}
	
	private Label addLabel(String string) {
		return new Label(string);
	}
	
	private Node addAttribute(Map<String,?> map, String key) throws GroovyInstantiationException {
		Field field = FieldFactory.makeField(map, key);
		return field.getControl();
	}

	public Node getNode() {
		return myPane;
	}
}
