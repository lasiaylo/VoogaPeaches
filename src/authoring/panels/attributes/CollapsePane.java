package authoring.panels.attributes;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import util.exceptions.GroovyInstantiationException;

import java.util.Map;

/**Displays a JavaFX TitledPane that holds a map
 * @author lasia
 *
 */
public class CollapsePane {

	private static final String MAP_NULL = "Map null";
	private final int LABEL_COL = 0;
	private final int ATTRIBUTE_COL = 1;
	private Updatable update;
	private boolean collapse;
	private Pane myPane;
	
	public CollapsePane(Map<String,?> map, Updatable update) throws GroovyInstantiationException {
		this(map, false, update);
	}
	
	public CollapsePane(Map<String,?> map, boolean collapse, Updatable update) throws GroovyInstantiationException {
		this.collapse = collapse;
		this.update = update;
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
		if(map == null) {
			return;
		}
		for (String s : map.keySet()) {
			Node node = addAttribute(map, s);
			TitledPane title = new TitledPane(s, node);
			title.setAnimated(false);
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
			grid.add(addDelete(map,s), ATTRIBUTE_COL+1, row);
			row++;
		}
		myPane = grid;
	}

	private Label addLabel(String string) {
		return new Label(string);
	}
	
	private Node addAttribute(Map<String,?> map, String key) throws GroovyInstantiationException {
		Map<String, Object> input = (Map<String, Object>) map;
		Field field = FieldFactory.makeFieldMap(input, key);
		return field.getControl();
	}

	private Node addDelete(Map<String, ?> map, String key){
		Map<String, Object> input = (Map<String, Object>) map;
		return new ParameterDelete(input, key, update).getNode();

	}

	public Node getNode() {
		return myPane;
	}
}