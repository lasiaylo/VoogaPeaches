package authoring.panels.tabbable;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

import authoring.panels.attributes.Field;
import authoring.panels.attributes.NumberInputField;
import engine.entities.Entity;
import engine.entities.Render;
import engine.entities.Transform;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import util.exceptions.GroovyInstantiationException;
import util.math.num.Vector;

public class TransformAttribute {
	private final String GET = "get";
	private final String SET = "set";
	private ResourceBundle myResources = ResourceBundle.getBundle("transform");
	private Transform myTransform;
	private GridPane myGrid;
	private TitledPane myPane;
	
	public TransformAttribute(Entity entity) throws GroovyInstantiationException {
		myTransform = entity.getTransform();
		myGrid = new GridPane();
		myPane = new TitledPane("Transform",null);
		myPane.setAnimated(false);
		myPane.setContent(myGrid);
		formatGrid();
	}

	private void formatGrid() throws GroovyInstantiationException {
		addRow("XPosition",0);
		addRow("YPosition",1);
	}

	private void addRow(String field, int row) throws GroovyInstantiationException {
		myGrid.add(makeLabel(field), 0, row);
		myGrid.add(makeField(field), 1, row);
	}
	
	private Label makeLabel(String labelName) {
		return new Label(labelName);
	}
	private Control makeField(String methodName) throws GroovyInstantiationException {
		try {
			Class<? extends Transform> clazz = myTransform.getClass();
			Method getPos = clazz.getDeclaredMethod(GET + methodName);
			Method setPos = clazz.getDeclaredMethod(SET + methodName, double.class);
			Field field = new NumberInputField(myTransform,getPos,setPos);
			TextField textfield = (TextField) field.getControl();
			textfield.setOnAction(e->test());
			return textfield;
			
		} catch (NoSuchMethodException | SecurityException e) {
			throw new GroovyInstantiationException();
		}
	}

	private void test() {
		System.out.println(myTransform.getXPosition());
	}
	public Node getNode() {
		return myPane;
	}
	
	
}
