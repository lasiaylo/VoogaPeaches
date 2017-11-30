package authoring.panels.tabbable;

import java.util.Set;

import authoring.panels.attributes.Field;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import util.exceptions.GroovyInstantiationException;

public class Attribute {
	private Object myAttribute;
	private Set<String> myMethods;
	private GridPane myGrid;
	private TitledPane myPane;
	
	public Attribute(Object attribute, String title, Set<String> methods) throws GroovyInstantiationException {
		myAttribute = attribute;
		myMethods = methods;
		myGrid = new GridPane();
		myPane = new TitledPane(title,null);
		myPane.setAnimated(false);
		myPane.setContent(myGrid);
		formatGrid();
	}

	private void formatGrid() throws GroovyInstantiationException {
		int row = 0;
		for (String method : myMethods) {
			addRow(method,row);
			row++;
		}
	}
	
	private void addRow(String fieldName, int row) throws GroovyInstantiationException {
		myGrid.add(makeLabel(fieldName), 0, row);
		Field field = FieldFactory.makeField(myAttribute, fieldName);
		myGrid.add(field.getControl(), 1, row);
	}
	
	private Label makeLabel(String labelName) {
		return new Label(labelName);
	}
	
	public Node getPane() {
		return myPane;
	}
}
