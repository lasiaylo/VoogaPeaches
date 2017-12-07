package authoring.panels.attributes;

import java.util.Map;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import util.exceptions.GroovyInstantiationException;

/**Displays a JavaFX TitledPane that holds a map
 * @author lasia
 *
 */
public class CollapsePane {
	private final int LABEL_COL = 0;
	private final int ATTRIBUTE_COL = 1;
	private boolean collapse;
	private TitledPane myCollapse;
	private Pane myPane;
	
	public CollapsePane(Map<String,?> map, String title) throws GroovyInstantiationException {
		this(map, title, false);
	}
	
	public CollapsePane(Map<String,?> map, String title, boolean collapse) throws GroovyInstantiationException {
		myCollapse = new TitledPane(title, myPane);
		myCollapse.setAnimated(false);
		addMap(map);
	}
	
	private void addMap(Map<String, ?> map) throws GroovyInstantiationException {
		if (collapse)
			formatCollapse(map);
		else
			formatGrid(map);
	}
	
	private void formatCollapse(Map<String, ?> map) throws GroovyInstantiationException {
		VBox vBox = new VBox();
		
		for (String s : map.keySet()) {
			Node node = addAttribute(map, s);
			TitledPane title = new TitledPane(s, node);
			vBox.getChildren().add(title);
		}
		myPane = vBox;
	}

	private void formatGrid(Map<String, ?> map) throws GroovyInstantiationException {
		GridPane grid = new GridPane();
		int row = 0;
		for (String s : map.keySet()) {
			grid.add(addLabel(s), LABEL_COL, row);
			grid.add(addAttribute(map, s), ATTRIBUTE_COL, row);
			row++;
		}
		myPane = grid;
	}

	private Label addLabel(String string) {
		return new Label(string);
	}
	
	private Node addAttribute(Map<String,?> map, String key) throws GroovyInstantiationException {
		Field field = FieldFactory.makeField(map, key);
		return field.getControl();
	}

	public Node getNode() {
		return myCollapse;
	}
}
